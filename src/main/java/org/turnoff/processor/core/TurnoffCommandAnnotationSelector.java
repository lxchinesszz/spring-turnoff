package org.turnoff.processor.core;

import org.turnoff.processor.annotation.TurnoffCommand;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @author liuxin
 * @version Id: HystrixCommandAnnotationSelector.java, v 0.1 2019-02-21 10:48
 */
public final class TurnoffCommandAnnotationSelector {

  private static Class<?>[] getParameterTypes(Object[] args) {
    if (null == args) {
      return null;
    }
    Class[] parameterClass = new Class[args.length];
    for (int i = 0; i < args.length; i++) {
      parameterClass[i] = args[i].getClass();
    }
    return parameterClass;
  }

  public static TurnoffCommandInvoker turnoffCommandSelector(Object proxy, Method method, Object[] args) {
    TurnoffCommand turnoffCommand = AnnotationUtils.findAnnotation(method, TurnoffCommand.class);
    Class<?>[] parameterType = getParameterTypes(args);
    if (null == turnoffCommand) {
      Method proxyMethod;
      try {
        proxyMethod = proxy.getClass().getMethod(method.getName(), parameterType);
      } catch (Exception n) {
        throw new RuntimeException("TurnoffCommand Exception:[" + proxy.getClass().getName() + " Can't  find annotation: @TurnoffCommand ]");
      }
      turnoffCommand = AnnotationUtils.findAnnotation(proxyMethod, TurnoffCommand.class);
    }
    if (null != turnoffCommand) {
      String fallbackMethod = turnoffCommand.fallbackMethod();
      Method agentMethod;
      try {
        agentMethod = proxy.getClass().getMethod(fallbackMethod, parameterType);
      } catch (Exception n) {
        throw new RuntimeException("TurnoffCommand Exception:[ Can't find TurnoffCommand fallbackMethod]");
      }
      return new TurnoffCommandInvoker(turnoffCommand, agentMethod);
    }
    return null;
  }

}
