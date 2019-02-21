package org.spring.processor;

import org.spring.processor.handler.CglibCircuitBreakerHandlerInvoker;
import org.spring.processor.handler.CircuitBreakerHandlerInvoker;
import org.springframework.aop.support.AopUtils;
import org.springframework.cglib.proxy.Proxy;

/**
 * @author liuxin
 * @version Id: CircuitBreakerHandlerInvokerAdapter.java, v 0.1 2019-02-20 13:52
 */
public class CircuitBreakerHandlerInvokerAdapter {

  private Object targetObject;

  protected CircuitBreakerHandlerInvokerAdapter(Object targetObject) {
    this.targetObject = targetObject;
  }

  protected Object invoke(ClassLoader classLoader, Class<?>[] interfaces) {
    boolean cglibProxy = AopUtils.isCglibProxy(targetObject);
    if (cglibProxy) {
      return Proxy.newProxyInstance(classLoader, interfaces, new CglibCircuitBreakerHandlerInvoker(targetObject));
    }
    return java.lang.reflect.Proxy.newProxyInstance(classLoader, interfaces, new CircuitBreakerHandlerInvoker(targetObject));
  }
}
