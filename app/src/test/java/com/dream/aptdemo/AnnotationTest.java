package com.dream.aptdemo;


import java.util.ArrayList;
import java.util.List;

/**
 * function: 注解
 *
 * @author zy
 * @since 2021/6/26
 */
public class AnnotationTest {


    public void client(){
        @SuppressWarnings("rawtypes")
        List list = new ArrayList();


        testDeprecated();
    }


    @Deprecated
    public void testDeprecated(){

    }



    @Override
    public String toString() {
        return super.toString();
    }
}




@FunctionalInterface
interface testInterface{
    void testMethod();
    //void testMethod2();
}
