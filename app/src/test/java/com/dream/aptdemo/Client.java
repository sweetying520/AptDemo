package com.dream.aptdemo;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * function: 测试类
 *
 * @author zy
 * @since 2021/6/25
 */
public class Client {

    @SuppressWarnings({"ConstantConditions", "rawtypes", "unchecked"})
    @Test
    public void test() {
        //非反射方式
        //new Business1().doBusiness1Function();

        //反射方式
        try {
            //获取文件
            File springConfigFile = new File("/Users/zhouying/AndroidStudioProjects/AptDemo/config.txt");
            //读取配置
            Properties config= new Properties();
            config.load(new FileInputStream(springConfigFile));
            //获取类路径
            String classPath = (String) config.get("class");
            //获取方法名
            String methodName = (String) config.get("method");

            //反射创建实际例子并调用方法
            Class aClass = Class.forName(classPath);
            Constructor declaredConstructor = aClass.getDeclaredConstructor();
            Object o = declaredConstructor.newInstance();
            Method declaredMethod = aClass.getDeclaredMethod(methodName);
            declaredMethod.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

//业务1
class Business1 {

    public void doBusiness1Function(){
        System.out.println("复杂业务功能1");
    }
}

//业务2
class Business2 {

    public void doBusiness2Function(){
        System.out.println("复杂业务功能2");
    }
}
