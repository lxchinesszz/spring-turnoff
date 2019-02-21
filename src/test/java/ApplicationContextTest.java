import nointerface.NoInterfaceService;
import org.spring.processor.CircuitBreakerBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.JdkUserService;
import service.JdkUserServiceImpl;

/**
 * @author liuxin
 * @version Id: ApplicationContextTest.java, v 0.1 2019-02-20 18:11
 */

public class ApplicationContextTest {

  /**
   * 只能在接口上定义
   *
   * @param args
   */
  public static void main(String[] args) {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext();
    app.refresh();
    app.register(JdkUserServiceImpl.class, NoInterfaceService.class);
    ConfigurableListableBeanFactory beanFactory = app.getBeanFactory();
    beanFactory.addBeanPostProcessor(new CircuitBreakerBeanPostProcessor());
    String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
    for (String beanName : beanDefinitionNames) {
      System.out.println(beanName);
    }
    JdkUserService jdkUserService = (JdkUserService)beanFactory.getBean("jdkUserServiceImpl");
    System.out.println(jdkUserService.getUserDesc("12312313"));
    JdkUserService jdkUserService1 = (JdkUserService)beanFactory.getBean(JdkUserService.class);
    System.out.println(jdkUserService1.getUserDesc("12312313"));


    NoInterfaceService bean = beanFactory.getBean(NoInterfaceService.class);
    System.out.println(bean.getBreakUserDesc("no interface"));
//
//    CircuitBreakerHandlerInvoker jdkHandler = new CircuitBreakerHandlerInvoker(new JdkUserServiceImpl());
//    JdkUserService userService = (JdkUserService)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{JdkUserService.class}, jdkHandler);
//    System.out.println(userService.getUserDesc("121321"));


  }


}
