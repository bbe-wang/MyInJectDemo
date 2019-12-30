package com.asyn.task.library;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ThinkPad on 2019/12/30.
 */

public class InjectManager {

    public static void inject(Activity activity){
        //布局注入
        injectLayout(activity);
        //控件注入
        injectViews(activity);
        //事件注入
        injrctEvents(activity);
    }

    private static void injrctEvents(Activity activity) {

         Class<? extends Activity> clazz = activity.getClass();
         //获取所有方法
        Method[] methods =clazz.getDeclaredMethods();
        for (Method method:methods    ) {
         //获取所有方法的注解---这个方法可能会有多个id
           Annotation[] annotations= method.getAnnotations();
            for (Annotation annotation:annotations ) {
            //获取onclick上的注解类型
              Class<? extends Annotation> annotationType=   annotation.annotationType();
              if (annotationType !=null){
                  EventBase eventBase=annotationType.getAnnotation(EventBase.class);
                  if (eventBase !=null){
                      //从这里面可以获取三个重要的成员
                      //设置的那个方法
                      String linsterSetter =eventBase.linsterSetter();
                      //设置点击事件的view
                      Class<?> listenerType =eventBase. listenerType();
                      //回调方法
                      String  callBackListener =eventBase.callBackListener();
                      //通过代理的方式操作这个对象  并且中间拦截onCLick这个方法执行自定义的方法


                      //通过annotationType获取onclicj注解值
                      try {
                          //int[] value 这个方法上的R.id  R.id 这些数值
                          Method valueMethod= annotationType.getDeclaredMethod("value");
                          //执行value方式 获取注解的值
                          int [] viewIds = (int[]) valueMethod.invoke(annotation);
                          // 代理方式（3个成员组合）
                          // 拦截方法
                          // 得到监听的代理对象（新建代理单例、类的加载器，指定要代理的对象类的类型、class实例）
                          // 得到监听的代理对象（新建代理单例、类的加载器，指定要代理的对象类的类型、class实例）
                          ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                          // 添加到拦截列表里面
                          handler.addMethod(callBackListener, method);
                          // 监听对象的代理对象
                          // ClassLoader loader:指定当前目标对象使用类加载器,获取加载器的方法是固定的
                          // Class<?>[] interfaces:目标对象实现的接口的类型,使用泛型方式确认类型
                          // InvocationHandler h:事件处理,执行目标对象的方法时,会触发事件处理器的方法
                         Object listener= Proxy.newProxyInstance(listenerType.getClassLoader(),new Class[]{listenerType},handler);
                          //遍历注解的值
                          for (int viewId : viewIds ) {
                              // 获得当前activity的view（赋值）
                              View view = activity.findViewById(viewId);
                              // 获取指定的方法
                              Method setter = view.getClass().getMethod(linsterSetter, listenerType); //获取 setOnclickListener 里面的参数 View.Onclick
                              // 执行方法
                              setter.invoke(view, listener); //执行setOnclickListener里面的回调 onclick方法
                          }

                      } catch (Exception e) {
                          e.printStackTrace();
                      }

                  }
              }


            }

        }


    }

    //最终目的是通过id获取到xml中的view
    private static void injectViews(Activity activity) {
     Class<? extends  Activity> clazz =activity.getClass();
     //获取自己类的所有属性
        Field[] fields= clazz.getDeclaredFields();
        //循环拿到所有属性的注解
        for (Field filed:fields  ) {
          InjectViews injectView= filed.getAnnotation(InjectViews.class);
          int viewId =injectView.value();
          //第一种获取view的范式
         //   View view = activity.findViewById(viewId);
            //第二种方式
            try {
                Method method = clazz.getMethod("findViewById", int.class);
                Object view =method.invoke(activity,viewId);
                filed.set(activity,view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private static void injectLayout(Activity activity) {
        //获取class对象
        Class< ? extends Activity> ckazz =activity.getClass();
        //通过对象获取类的注解
        ContentView contentView = ckazz.getAnnotation(ContentView.class);
        if (contentView !=null) {
            //获取这个注解的值
            int layoutId = contentView.value();
            //可以直接设置
          //  activity.setContentView(layoutId);
            //第二种方法 通过反射
            try {
                Method method =ckazz.getMethod("setContentView", int.class);
                method.invoke(activity,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }



}
