package org.turnoff.processor.handler;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.turnoff.processor.exception.FallbackMethodException;
import org.turnoff.processor.core.TurnoffCommandAnnotationSelector;
import org.turnoff.processor.core.TurnoffCommandInvoker;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liuxin
 * @version Id: CircuitBreakerHandlerInvoker2.java, v 0.1 2019-02-21 14:57
 */
public class CircuitBreakerHandlerInvoker implements MethodInterceptor {


    private static Map<Method, AtomicLong> methodAtomicInvokerCountMap = new ConcurrentHashMap<Method, AtomicLong>();


    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object invoke;
        Method invocationMethod = methodInvocation.getMethod();
        Object targetObject = methodInvocation.getThis();
        Object[] arguments = methodInvocation.getArguments();
        TurnoffCommandInvoker turnoffCommandInvoker = TurnoffCommandAnnotationSelector.turnoffCommandSelector(targetObject, invocationMethod, arguments);
        try {
            invoke = methodInvocation.proceed();
        } catch (Exception e) {
            try {
                if (null != turnoffCommandInvoker) {
                    invoke = turnoffCommandInvoker.invoke(targetObject, arguments);
                    AtomicLong atomicTurnOffCount = methodAtomicInvokerCountMap.get(invocationMethod);
                    if (null == atomicTurnOffCount) {
                        atomicTurnOffCount = new AtomicLong(0);
                    }
                    atomicTurnOffCount.getAndIncrement();
                    methodAtomicInvokerCountMap.put(invocationMethod, atomicTurnOffCount);
                } else {
                    throw new RuntimeException("Can't find turnoffCommand", e);
                }
            } catch (FallbackMethodException fallbackError) {
                throw fallbackError;
            }
        }
        return invoke;
    }
}
