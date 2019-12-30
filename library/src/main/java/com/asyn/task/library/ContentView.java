package com.asyn.task.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

/**
 * Created by ThinkPad on 2019/12/30.
 */

@Target(ElementType.TYPE)//该注解作用在类上
@Retention(RetentionPolicy.RUNTIME)//该注解作用在运行时
public @interface ContentView {

    int value();


}
