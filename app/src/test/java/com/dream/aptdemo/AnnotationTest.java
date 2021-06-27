package com.dream.aptdemo;


import androidx.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * function: 注解
 *
 * @author zy
 * @since 2021/6/26
 */
public class AnnotationTest {


    @org.junit.Test
    public void client(){
        @SuppressWarnings("rawtypes")
        List list = new ArrayList();





        testDeprecated();


        EnumFontType type1 = EnumFontType.ROBOTO_BOLD;

        @AnnotationFontType int type2 = AnnotationFontType.ROBOTO_MEDIUM;

        new GasStation().addOil(200);


        //============================================注解健壮性============================
        Class<Test> testClass = Test.class;
        //获取当前注解是否存在
        boolean annotationPresent = testClass.isAnnotationPresent(TestAnnotation.class);
        //如果存在则进入条件体
        if(annotationPresent){
            TestAnnotation declaredAnnotation = testClass.getDeclaredAnnotation(TestAnnotation.class);
            System.out.println(declaredAnnotation.name());
            System.out.println(declaredAnnotation.age());
        }


        //============================================获取属性注解============================
        Class<Test> testClass1 = Test.class;
        try {
            Field field = testClass1.getDeclaredField("test");
            if(field.isAnnotationPresent(TestField.class)){
                TestField fieldAnnotation = field.getDeclaredAnnotation(TestField.class);
                System.out.println(fieldAnnotation.filed());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        //============================================获取方法注解============================
        Class<Test> testClass2 = Test.class;
        try {
            Method method = testClass2.getDeclaredMethod("test");
            if(method.isAnnotationPresent(TestMethod.class)){
                TestMethod methodAnnotation = method.getDeclaredAnnotation(TestMethod.class);
                System.out.println(methodAnnotation.method());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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




//游戏玩家注解
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface GamePlayer{
    Game[] value();
}

//游戏注解
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(GamePlayer.class)
@interface Game{
    String gameName();
}

@Game(gameName = "CF")
@Game(gameName = "LOL")
@Game(gameName = "DNF")
class GameTest{

}

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface TestAnnotation{

    //这就是注解属性的语法结构

    //定义一个属性并给了默认值
    String name() default "erdai";

    //定义一个属性未给默认值
    int age() default 0;

}

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface TestField{
    String filed();
}

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TestMethod{
    String method();
}


//@TestAnnotation(age = 18)
//class Test{
//
//}

@TestAnnotation(age = 18,name = "erdai666")
class Test{

    @TestField(filed = "我是属性")
    public String test;

    @TestMethod(method = "我是方法")
    public void test(){

    }
}

enum EnumFontType{
    ROBOTO_REGULAR,ROBOTO_MEDIUM,ROBOTO_BOLD
}


@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
@IntDef({AnnotationFontType.ROBOTO_REGULAR,AnnotationFontType.ROBOTO_MEDIUM,AnnotationFontType.ROBOTO_BOLD})
@interface AnnotationFontType{
    int ROBOTO_REGULAR = 1;
    int ROBOTO_MEDIUM = 2;
    int ROBOTO_BOLD = 3;
}


@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface OilAnnotation{
    double maxOilMoney() default 0;
}

class GasStation{


    @OilAnnotation(maxOilMoney = 200)
    public void addOil(double money){
        String tips = processOilAnnotation(money);
        System.out.println(tips);
    }


    @SuppressWarnings("all")
    private String processOilAnnotation(double money){
        try {
            Class<GasStation> aClass = GasStation.class;
            Method addOilMethod = aClass.getDeclaredMethod("addOil", double.class);
            boolean annotationPresent = addOilMethod.isAnnotationPresent(OilAnnotation.class);
            if(annotationPresent){
                OilAnnotation oilAnnotation = addOilMethod.getDeclaredAnnotation(OilAnnotation.class);
                if(money >= oilAnnotation.maxOilMoney()){
                    return "油已加满";
                }else {
                    return "加油中...";
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "加油失败";
    }

}