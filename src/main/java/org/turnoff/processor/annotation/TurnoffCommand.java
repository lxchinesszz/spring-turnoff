package org.turnoff.processor.annotation;

import java.lang.annotation.*;

/**
 * @author liuxin
 * @version Id: TurnoffCommand.java, v 0.1 2019-02-21 16:35
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TurnoffCommand {
  /**
   * 断路方法,当被改注解标记的方法,执行失败
   * 则执行断路器中配置的回调方法
   * @return
   */
  String fallbackMethod();

}
