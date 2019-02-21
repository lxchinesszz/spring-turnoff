package org.turnoff.processor.core;

import org.turnoff.processor.annotation.TurnoffCommand;
import org.turnoff.processor.exception.FallbackMethodException;

import java.lang.reflect.Method;

/**
 * @author liuxin
 * @version Id: HystrixCommandMetadata.java, v 0.1 2019-02-21 10:49
 */
public class TurnoffCommandInvoker {

  private final TurnoffCommand turnoffCommand;

  private final Method agentMethod;

  protected TurnoffCommandInvoker(TurnoffCommand turnoffCommand, Method agentMethod) {
    this.turnoffCommand = turnoffCommand;
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
