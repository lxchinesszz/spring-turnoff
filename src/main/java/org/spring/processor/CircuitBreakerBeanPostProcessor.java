package org.spring.processor;

import org.spring.processor.annotation.HystrixCommand;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.reflect.Method;


/**
 * @author liuxin
 * @version Id: CircuitBreakerBeanPostProcessor.java, v 0.1 2019-02-20 11:28
 */
public class CircuitBreakerBeanPostProcessor implements BeanPostProcessor, Ordered {

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
        Method[] declaredMethods = o.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            HystrixCommand annotation = AnnotationUtils.findAnnotation(method, HystrixCommand.class);
            if (null != annotation) {
                Class<?>[] interfaces = o.getClass().getInterfaces();
                ClassLoader classLoader = o.getClass().getClassLoader();
                return new CircuitBreakerHandlerInvokerAdapter(o).invoke(classLoader, interfaces);
            }
        }
        return o;

    }


    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
