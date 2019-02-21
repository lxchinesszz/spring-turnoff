package org.spring.processor.handler;

import org.spring.processor.FallbackMethodException;
import org.spring.processor.HystrixCommandAnnotationSelector;
import org.spring.processor.HystrixCommandInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author liuxin
 * @version Id: CircuitBreakerHandlerInvoker.java, v 0.1 2019-02-20 11:37
 */
public class CircuitBreakerHandlerInvoker implements InvocationHandler {

    private Object targetObject;


    public CircuitBreakerHandlerInvoker(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke;
        HystrixCommandInvoker hystrixCommandMetadata = HystrixCommandAnnotationSelector.hystrixCommandSelector(targetObject, method, args);
        try {
            invoke = method.invoke(targetObject, args);
        } catch (Exception e) {
            try {
                if (null != hystrixCommandMetadata) {
                    invoke = hystrixCommandMetadata.invoke(targetObject, args);
                } else {
                    throw new RuntimeException("Can't  find hystrixCommand",e);
                }
            } catch (FallbackMethodException fallbackError) {
                throw fallbackError;
            }
        }
        return invoke;
    }
}
