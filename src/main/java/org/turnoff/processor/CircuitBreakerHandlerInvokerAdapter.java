package org.turnoff.processor;


import org.turnoff.processor.proxy.TurnoffProxyFactory;
import org.springframework.aop.support.AopUtils;
/**
 * @author liuxin
 * @version Id: CircuitBreakerHandlerInvokerAdapter.java, v 0.1 2019-02-20 13:52
 */
public class CircuitBreakerHandlerInvokerAdapter {

  private Object targetObject;

  private TurnoffProxyFactory proxyFactory = new TurnoffProxyFactory();

  protected CircuitBreakerHandlerInvokerAdapter(Object targetObject) {
    this.targetObject = targetObject;
  }

  protected Object getProxy() {
    boolean cglibProxy = AopUtils.isCglibProxy(targetObject);
    if (cglibProxy) {
      return proxyFactory.getCglibProxy(targetObject);
    }
    Class<?>[] interfaces = targetObject.getClass().getInterfaces();
    if (null != interfaces && interfaces.length >= 0) {
      return proxyFactory.getCglibProxy(targetObject);
    }
    return proxyFactory.getJdkProxy(targetObject);
  }
}
