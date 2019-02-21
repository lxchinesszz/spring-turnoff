package org.spring.processor.handler;

import org.spring.processor.annotation.HystrixCommand;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @author liuxin
 * @version Id: CglibCircuitBreakerHandlerInvoker.java, v 0.1 2019-02-20 13:49
 */
public class CglibCircuitBreakerHandlerInvoker implements InvocationHandler {

  private Object targetObject;

  public CglibCircuitBreakerHandlerInvoker(Object targetObject) {
    this.targetObject = targetObject;
  }

  public Object invoke(Object o, Method method, Object[] args) throws Throwable {
    Method breakerMethod = null;
    Object invoke = null;
    HystrixCommand annotation = AnnotationUtils.findAnnotation(method, HystrixCommand.class);
    if (null != annotation) {
      String fallbackMethod = annotation.fallbackMethod();
      Class[] parameterClass = new Class[args.length];
      for (int i = 0; i < args.length; i++) {
        parameterClass[i] = args[i].getClass();
      }
      breakerMethod = targetObject.getClass().getMethod(fallbackMethod, parameterClass);
    }
    try {
      invoke = method.invoke(targetObject, args);
    } catch (Exception e) {
      if (null != breakerMethod) {
        invoke = breakerMethod.invoke(targetObject, args);
      }
    }
    return invoke;
  }
}
