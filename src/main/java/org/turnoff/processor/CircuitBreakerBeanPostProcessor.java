package org.turnoff.processor;

import org.turnoff.processor.annotation.TurnoffCommand;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import java.lang.reflect.Method;

/**
 * @author liuxin
 * @version Id: CircuitBreakerBeanPostProcessor.java, v 0.1 2019-02-20 11:28
 */
public class CircuitBreakerBeanPostProcessor implements BeanPostProcessor, Ordered {

    private  static MethodMatcher methodMatcher = AnnotationMatchingPointcut.forMethodAnnotation(TurnoffCommand.class).getMethodMatcher();

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }
    /**
     * 生成代理
     *
     * @param o
     * @param s
     * @return
     * @throws BeansException
     */
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Class<?> targetClass = o.getClass();
        Method[] declaredMethods = targetClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (methodMatcher.matches(method, targetClass)) {
                return new CircuitBreakerHandlerInvokerAdapter(o).getProxy();
            }
        }
        return o;

    }

    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
