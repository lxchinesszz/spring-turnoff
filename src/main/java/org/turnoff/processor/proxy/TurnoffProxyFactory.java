package org.turnoff.processor.proxy;

import org.turnoff.processor.annotation.TurnoffCommand;
import org.turnoff.processor.handler.CircuitBreakerHandlerInvoker;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @author liuxin
 * @version Id: proxy.java, v 0.1 2019-02-21 15:20
 */
public class TurnoffProxyFactory {

    private final AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(TurnoffCommand.class);

    private CircuitBreakerHandlerInvoker circuitBreakerHandlerInvoker = new CircuitBreakerHandlerInvoker();

    public Object getCglibProxy(Object target) {
        return getProxy(target, pointcut, false);
    }

    public Object getJdkProxy(Object target) {
        return getProxy(target, pointcut, true);
    }

    private Object getProxy(Object target, Pointcut pointcut, boolean isJdkProxy) {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(circuitBreakerHandlerInvoker);
        ProxyFactory weaver = new ProxyFactory(target);
        weaver.addAdvisor(advisor);
        if (isJdkProxy) {
            weaver.setInterfaces(target.getClass().getInterfaces());
        }
        return weaver.getProxy();

    }


}
