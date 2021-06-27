package com.dream.apt_processor;

import com.dream.apt_annotation.AptAnnotation;
import com.dream.apt_annotation.MyBindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


@AutoService(Processor.class)
@SupportedOptions("MODULE_NAME")
@SupportedAnnotationTypes("com.dream.apt_annotation.AptAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AptAnnotationProcessor extends AbstractProcessor {


    Filer filer;
    private String mModuleName;

    /**
     * 做一些初始化的工作
     *
     * @param processingEnvironment 这个参数提供了若干工具类，供编写生成 Java 类时所使用
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        mModuleName = processingEnv.getOptions().get("MODULE_NAME");
    }

    /**
     * 编写生成 Java 类的相关逻辑
     *
     * @param set              支持处理的注解集合
     * @param roundEnvironment 通过该对象查找指定注解下的节点信息
     * @return true: 表示注解已处理，后续注解处理器无需再处理它们；false: 表示注解未处理，可能要求后续注解处理器处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty()) {
            return false;
        }

        Set<? extends Element> rootElements = roundEnvironment.getElementsAnnotatedWith(AptAnnotation.class);


        // 构建主函数
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test")
                .addModifiers(Modifier.PUBLIC) // 指定方法修饰符
                .returns(void.class) // 指定返回类型
                .addParameter(String.class, "param"); // 添加参数
        builder.addStatement("$T.out.println($S)", System.class, "模块: " + mModuleName);

        if (rootElements != null && !rootElements.isEmpty()) {
            for (Element element : rootElements) {
                String elementName = element.getSimpleName().toString();
                String desc = element.getAnnotation(AptAnnotation.class).desc();
                // 构建方法体
                builder.addStatement("$T.out.println($S)", System.class, "节点: " + elementName + "  " + "描述: " + desc);
            }
        }
        MethodSpec main =builder.build();

        // 构建类
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC) // 指定类修饰符
                .addMethod(main) // 添加方法
                .build();

        // 指定包路径，构建文件体
        JavaFile javaFile = JavaFile.builder("com.dream.aptdemo", helloWorld).build();
        try {
            // 创建文件
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    /**
     * 接收外来传入的参数，最常用的形式就是在 Gradle 里 javaCompileOptions 配置的属性
     *
     * @return 属性的 Set 集合
     */
//    @Override
//    public Set<String> getSupportedOptions() {
//        return Collections.singleton("MODULE_NAME");
//    }

    /**
     * 当前注解处理器支持的注解集合，如果支持，就会调用 process 方法
     *
     * @return 支持的注解集合
     */
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        //只支持 AptAnnotation 注解
//        return Collections.singleton(AptAnnotation.class.getCanonicalName());
//    }

    /**
     * 编译当前注解处理器的 JDK 版本
     *
     * @return JDK 版本
     */
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.RELEASE_8;
//    }


}