package org.spring.processor;

import org.spring.processor.annotation.HystrixCommand;

import java.lang.reflect.Method;

/**
 * @author liuxin
 * @version Id: HystrixCommandMetadata.java, v 0.1 2019-02-21 10:49
 */
public class HystrixCommandInvoker {

  private final HystrixCommand hystrixCommand;

  private final Method agentMethod;

  protected HystrixCommandInvoker(HystrixCommand hystrixCommand, Method agentMethod) {
    this.hystrixCommand = hystrixCommand;
    this.agentMethod = agentMethod;
  }

  public Object invoke(Object targetObject, Object[] args) throws FallbackMethodException {
    Object invoke;
    try {
      invoke = agentMethod.invoke(targetObject, args);
    } catch (Exception e) {
      throw new FallbackMethodException("AgentMethod [" + agentMethod.getName() + "] Invoker Exception");
    }
    return invoke;
  }
}
