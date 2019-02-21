package org.spring.processor.springhandler;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.spring.processor.FallbackMethodException;
import org.spring.processor.HystrixCommandAnnotationSelector;
import org.spring.processor.HystrixCommandInvoker;

import java.lang.reflect.Method;

/**
 * @author liuxin
 * @version Id: CircuitBreakerHandlerInvoker2.java, v 0.1 2019-02-21 14:57
 */
public class CircuitBreakerHandlerInvoker2 implements MethodInterceptor {

  private Object targetObject;

  public CircuitBreakerHandlerInvoker2(Object targetObject) {
    this.targetObject = targetObject;
  }

  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    Object invoke;
    Method invocationMethod = methodInvocation.getMethod();
    Object[] arguments = methodInvocation.getArguments();
    HystrixCommandInvoker hystrixCommandMetadata = HystrixCommandAnnotationSelector.hystrixCommandSelector(targetObject, invocationMethod, arguments);
    try {
      invoke = methodInvocation.proceed();
    } catch (Exception e) {
      try {
        if (null != hystrixCommandMetadata) {
          invoke = hystrixCommandMetadata.invoke(targetObject, arguments);
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
