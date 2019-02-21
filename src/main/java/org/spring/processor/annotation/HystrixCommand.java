package org.spring.processor.annotation;


import java.lang.annotation.*;

/**
 * @author liuxin
 * @version Id: HystrixCommand.java, v 0.1 2019-02-20 11:32
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HystrixCommand {
  /**
   * 回调方法
   * @return
   */
  String fallbackMethod();
}
