package proxy;

import nointerface.NoInterfaceService;
import org.spring.processor.springhandler.CircuitBreakerHandlerInvoker2;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import service.JdkUserService;
import service.JdkUserServiceImpl;

import javax.xml.ws.Action;

/**
 * @author liuxin
 * @version Id: SpringProxyTest.java, v 0.1 2019-02-21 15:03
 */
public class SpringProxyTest {
  public static void main(String[] args) {
    cglibTest();
    jdkTest();
  }

  private static void cglibTest() {
    NoInterfaceService noInterfaceService = new NoInterfaceService();
    DefaultPointcutAdvisor advisor3 = new DefaultPointcutAdvisor();
    AnnotationMatchingPointcut annotationMatchingPointcut = new AnnotationMatchingPointcut(Component.class, Action.class);
    advisor3.setPointcut(annotationMatchingPointcut);
    advisor3.setAdvice(new CircuitBreakerHandlerInvoker2(noInterfaceService));
    ProxyFactory weaver = new ProxyFactory(noInterfaceService);
    weaver.addAdvisor(advisor3);
    //返回代理对象
    NoInterfaceService proxy = (NoInterfaceService) weaver.getProxy();
    System.out.println(proxy.getUserDesc("cglib代理"));
  }

  private static void jdkTest(){
    JdkUserServiceImpl jdkUserService = new JdkUserServiceImpl();
    DefaultPointcutAdvisor advisor3 = new DefaultPointcutAdvisor();
    AnnotationMatchingPointcut annotationMatchingPointcut = new AnnotationMatchingPointcut(Service.class, Action.class);
    advisor3.setPointcut(annotationMatchingPointcut);
    advisor3.setAdvice(new CircuitBreakerHandlerInvoker2(jdkUserService));
    ProxyFactory weaver = new ProxyFactory(jdkUserService);
    weaver.addAdvisor(advisor3);
    weaver.setInterfaces(JdkUserService.class);
    //返回代理对象
    JdkUserService proxy = (JdkUserService) weaver.getProxy();
    System.out.println(proxy.getUserDesc("Jdk代理"));
  }

}
