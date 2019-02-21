package org.spring.processor;

import org.spring.processor.annotation.HystrixCommand;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @author liuxin
 * @version Id: HystrixCommandAnnotationSelector.java, v 0.1 2019-02-21 10:48
 */
public final class HystrixCommandAnnotationSelector {

  private static Class<?>[] getParamterType(Object[] args) {
    if (null == args) {
      return null;
    }
    Class[] parameterClass = new Class[args.length];
    for (int i = 0; i < args.length; i++) {
      parameterClass[i] = args[i].getClass();
    }
    return parameterClass;
  }

  public static HystrixCommandInvoker hystrixCommandSelector(Object proxy, Method method, Object[] args) {
    HystrixCommand hystrixCommand = AnnotationUtils.findAnnotation(method, HystrixCommand.class);
    Class<?>[] paramterType = getParamterType(args);
    if (null == hystrixCommand) {
      Method proxyMethod;
      try {
        proxyMethod = proxy.getClass().getMethod(method.getName(), paramterType);
      } catch (Exception n) {
        throw new RuntimeException("HystrixCommand Exception:[" + proxy.getClass().getName() + " Can't  find annotation: HystrixCommand ]");
      }
      hystrixCommand = AnnotationUtils.findAnnotation(proxyMethod, HystrixCommand.class);
    }
    if (null != hystrixCommand) {
      String fallbackMethod = hystrixCommand.fallbackMethod();
      Method agentMethod;
      try {
        agentMethod = proxy.getClass().getMethod(fallbackMethod, paramterType);
      } catch (Exception n) {
        throw new RuntimeException("HystrixCommand Exception:[ Can't find HystrixCommand fallbackMethod]");
      }
      return new HystrixCommandInvoker(hystrixCommand, agentMethod);
    }
    return null;
  }

}
