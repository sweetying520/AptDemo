package com.dream.apt_processor;

import com.dream.apt_annotation.ViewCreator;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * function: View 生成注解处理器
 *
 * @author zy
 * @since 2021/6/29
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.dream.apt_annotation.ViewCreator")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyViewCreatorProcessor extends AbstractProcessor {

    /**文件生成器*/
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //从文件中读取控件名称，并转换成对应的集合
        Set<String> mViewNameSet = readViewNameFromFile();
        //如果获取的控件名称集合为空，则终止流程
        if(mViewNameSet == null || mViewNameSet.isEmpty()){
            return false;
        }


        //获取注解使用注解元素
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(ViewCreator.class);
        for (Element element : elementsAnnotatedWith) {
            System.out.println("Hello " + element.getSimpleName() + ", 欢迎使用 APT");
            startGenerateCode(mViewNameSet);
            //如果有多个地方标注了注解，我们只读取第一次的就行了
            break;
        }


        return true;
    }

    /**
     * 开始执行生成代码的逻辑
     *
     * @param mViewNameSet 控件名称集合
     */
    private void startGenerateCode(Set<String> mViewNameSet) {
        System.out.println("开始生成 Java 类...");
        System.out.println("a few moment later...");
        //=========================================== 构建方法 start ==============================================
        //1、构建方法：方法名，注解，修饰符，返回值，参数

        ClassName viewType = ClassName.get("android.view","View");
        MethodSpec.Builder methodBuilder = MethodSpec
                //方法名
                .methodBuilder("createView")
                //注解
                .addAnnotation(Override.class)
                //修饰符
                .addModifiers(Modifier.PUBLIC)
                //返回值
                .returns(viewType)
                //第一个参数
                .addParameter(String.class,"name")
                //第二个参数
                .addParameter(ClassName.get("android.content","Context"),"context")
                //第三个参数
                .addParameter(ClassName.get("android.util","AttributeSet"),"attr");

        //2、构建方法体
        methodBuilder.addStatement("$T view = null",viewType);
        methodBuilder.beginControlFlow("switch(name)");
        //循环遍历控件名称集合
        for (String viewName : mViewNameSet) {
            if(viewName.contains(".")){
                //针对包含 . 的控件名称进行处理
                //分离包名和控件名，如：androidx.constraintlayout.widget.ConstraintLayout
                //packageName：androidx.constraintlayout.widget
                //simpleViewName：ConstraintLayout
                String packageName = viewName.substring(0,viewName.lastIndexOf("."));
                String simpleViewName = viewName.substring(viewName.lastIndexOf(".") + 1);
                ClassName returnType = ClassName.get(packageName, simpleViewName);

                methodBuilder.addCode("case $S:\n",viewName);
                methodBuilder.addStatement("\tview = new $T(context,attr)", returnType);
                methodBuilder.addStatement("\tbreak");
            }
        }
        methodBuilder.addCode("default:\n");
        methodBuilder.addStatement("\tbreak");
        methodBuilder.endControlFlow();
        methodBuilder.addStatement("return view");

        MethodSpec createView = methodBuilder.build();
        //=========================================== 构建方法 end ==============================================

        //=========================================== 构建类 start ==============================================
        TypeSpec myViewCreatorImpl = TypeSpec.classBuilder("MyViewCreatorImpl")
                //类修饰符
                .addModifiers(Modifier.PUBLIC)
                //实现接口
                .addSuperinterface(ClassName.get("com.dream.apt_api", "IMyViewCreator"))
                //添加方法
                .addMethod(createView)
                .build();
        //=========================================== 构建类 end ================================================

        //=========================================== 指定包路径，构建文件体 start =================================、
        //指定类包路径
        JavaFile javaFile = JavaFile.builder("com.dream.aptdemo",myViewCreatorImpl).build();
        //生成文件
        try {
            javaFile.writeTo(mFiler);
            System.out.println("生成成功...");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("生成失败...");
        }
        //=========================================== 指定包路径，构建文件体 end ===================================
    }


    /**
     * 从文件中读取控件名称，并转换成对应的集合
     */
    private Set<String> readViewNameFromFile() {
        try {
            //获取存储控件名称的文件
            File file = new File("/Users/zhouying/AndroidStudioProjects/AptDemo/all_view_name.txt");
            Properties config = new Properties();
            config.load(new FileInputStream(file));
            //获取控件名称集合
            return config.stringPropertyNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
