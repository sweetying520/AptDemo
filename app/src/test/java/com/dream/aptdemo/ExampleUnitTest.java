package com.dream.aptdemo;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;


//自定义注解1
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation1 {

}

//自定义注解2
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation2 {

}

//自定义注解3
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation3{

}

//接口
interface ICar {
    void combine();
}

//车
@CustomAnnotation3
class Car<K,V> {
    private String carDesign = "设计稿";
    public String engine = "发动机";

    public void run(long kilometer) {
        System.out.println("Car run " + kilometer + " km");
    }
}
//==============================上面这些都是为下面这台奔驰服务的😂===========================
//奔驰
@CustomAnnotation1
@CustomAnnotation2
class Benz extends Car<String,Integer> implements ICar {

    private String carName = "奔驰";
    public String carColor = "白色";

    public Benz() {
    }

    private Benz(String carName) {
        this.carName = carName;
    }

    public Benz(String carName, String carColor) {
        this.carName = carName;
        this.carColor = carColor;
    }

    @Override
    public void combine() {
        System.out.println("组装一台奔驰");
    }

    private void privateMethod(String params){
        System.out.println("我是私有方法: " + params);
    }
}


public class ExampleUnitTest {

    @SuppressWarnings({"rawtypes", "ConstantConditions", "unchecked"})
    @Test
    public void test() throws Exception {
        //3 种方式去获取类对象
        Benz benz = new Benz();
        Class benzClass = Benz.class;
        Class benzClass1 = benz.getClass();
        Class benzClass2 = Class.forName("com.dream.aptdemo.Benz");

        //获取类名
        String className = benzClass.getSimpleName();
        System.out.println(className);

        System.out.println();

        //获取类路径
        String classPath1 = benzClass.getName();
        String classPath2 = benzClass.getCanonicalName();
        System.out.println(classPath1);
        System.out.println(classPath2);

        System.out.println();

        //获取父类
        String fatherClassName = benzClass.getSuperclass().getSimpleName();
        System.out.println(fatherClassName);

        System.out.println();

        //获取接口
        Class[] interfaces = benzClass.getInterfaces();
        for (Class anInterface : interfaces) {
            System.out.println(anInterface.getName());
        }

        System.out.println();

        //获取构造器创建实例对象
        //获取构造器
        Constructor constructor = benzClass.getDeclaredConstructor();
        //创建实例
        Benz myBenz = (Benz) constructor.newInstance();
        //修改属性
        myBenz.carColor = "黑色";
        myBenz.combine();
        System.out.println(myBenz.carColor);

        //获取单个属性
        Field carName = benzClass.getDeclaredField("carName");

        System.out.println();

        //获取本类全部属性
        Field[] declaredFields = benzClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println("属性: " + declaredField.getName());
        }

        System.out.println();

        //获取本类及继承的类全部 public 修饰的属性
        Field[] fields = benzClass.getFields();
        for (Field field : fields) {
            System.out.println("属性: " + field.getName());
        }


        System.out.println();

        //设置访问私有变量
        carName.setAccessible(true);

        System.out.println();

        //获取属性名
        System.out.println(carName.getName());

        System.out.println();

        //获取变量类型
        System.out.println(carName.getType().getName());

        System.out.println();

        //获取对象中该属性的值
        System.out.println(carName.get(benz));

        System.out.println();

        //给属性设置值
        carName.set(benz,"sweetying");
        System.out.println(carName.get(benz));

        System.out.println();

        //获取单个 public 方法
        Method publicMethod = benzClass.getMethod("combine");

        //获取单个 private 方法
        Method privateMethod = benzClass.getDeclaredMethod("privateMethod",String.class);

        System.out.println();

        //获取全部方法
        //获取本类及继承的类全部 public 修饰的方法
        Method[] methods = benzClass.getMethods();
        for (Method method : methods) {
            System.out.println("方法名: " + method.getName());
        }

        System.out.println();

        //获取本类全部方法
        Method[] declaredMethods = benzClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println("方法名: " + declaredMethod.getName());
        }


        System.out.println();

        //方法调用
        privateMethod.setAccessible(true);
        privateMethod.invoke(benz,"接收传入的参数");

        System.out.println();

        //获取单个 public 修饰的构造方法
        Constructor singleConstructor = benzClass.getConstructor(String.class,String.class);

        //获取单个构造方法
        Constructor declaredConstructor = benzClass.getDeclaredConstructor(String.class);



        //获取本类全部构造方法
        Constructor[] declaredConstructors = benzClass.getDeclaredConstructors();
        for (Constructor declaredConstructor1 : declaredConstructors) {
            System.out.println("构造方法: " + declaredConstructor1);
        }


        System.out.println();

        //获取全部 public 构造方法, 经测试不包含父类的构造方法
        Constructor[] constructors = benzClass.getConstructors();
        for (Constructor constructor1 : constructors) {
            System.out.println("构造方法: " + constructor1);
        }

        System.out.println();

        //构造方法实例化
        //1
        declaredConstructor.setAccessible(true);
        Benz declareBenz = (Benz) declaredConstructor.newInstance("");
        System.out.println(declareBenz.carColor);

        System.out.println();

        //2
        Benz singleBenz = (Benz) singleConstructor.newInstance("奔驰 S ","香槟金");
        System.out.println(singleBenz.carColor);

        System.out.println();
        //获取父类泛型
        Type genericType = benzClass.getGenericSuperclass();
        if (genericType instanceof ParameterizedType) {
            Type[] actualType = ((ParameterizedType) genericType).getActualTypeArguments();
            for (Type type : actualType) {
                System.out.println(type.getTypeName());
            }
        }

        System.out.println();

        //获取单个本类或父类注解
        Annotation annotation1 = benzClass.getAnnotation(CustomAnnotation1.class);
        System.out.println(annotation1.annotationType().getSimpleName());
        Annotation annotation3 = benzClass.getAnnotation(CustomAnnotation3.class);
        System.out.println(annotation3.annotationType().getSimpleName());

        System.out.println();
        //获取单个本类注解
        Annotation declaredAnnotation1 = benzClass.getDeclaredAnnotation(CustomAnnotation2.class);
        System.out.println(declaredAnnotation1.annotationType().getSimpleName());

        System.out.println();

        //获取全部注解
        Annotation[] annotations = benzClass.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("注解名称: " + annotation.annotationType().getSimpleName());
        }

        System.out.println();

        Annotation[] declaredAnnotations = benzClass.getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            System.out.println("注解名称: " + declaredAnnotation.annotationType().getSimpleName());
        }
    }
}