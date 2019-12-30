package com.asyn.task.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by ThinkPad on 2019/12/30.
 */
// 将回调的onClick方法拦截，执行我们自己自定义的方法（aop概念）  因为onclick  onLongClick都需要执行这个方法  所说义给给他切面
public class ListenerInvocationHandler implements InvocationHandler {

    // 需要拦截的对象
    private Object target;
    // 需要拦截的对象键值对
    private HashMap<String, Method> methodHashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method 等于拦截的onclick
        if (target != null) {
            // 获取需要拦截的方法名
            String methodName = method.getName(); // 假如是onClick
            // 重新赋值，将拦截的方法换为show
            method = methodHashMap.get(methodName); // 执行拦截的方法，show
            if (method != null) {
                return method.invoke(target, args);
            }
        }
        return null;
    }

    /**
     * 将需要拦截的方法添加
     * @param methodName 需要拦截的方法，如：onClick()
     * @param method 执行拦截后的方法，如：show()
     */
    public void addMethod(String methodName, Method method) {
        methodHashMap.put(methodName, method);
    }
}


