package com.asyn.task.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ThinkPad on 2019/12/30.
 */
@Target(ElementType.ANNOTATION_TYPE)//作用在注解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //事件的三部曲
    //setOnclickListener
    String  linsterSetter();

    //view.serOnClickListener
    Class<?> listenerType();

    // 回调方法
    String callBackListener();


}
