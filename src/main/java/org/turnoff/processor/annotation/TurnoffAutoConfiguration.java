package org.turnoff.processor.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.turnoff.processor.CircuitBreakerBeanPostProcessor;

/**
 * @author liuxin
 * @version Id: EnableTurnoff.java, v 0.1 2019-02-21 17:18
 */
@Configuration
public class TurnoffAutoConfiguration {

  @Bean(value = "circuitBreakerBeanPostProcessor")
  public CircuitBreakerBeanPostProcessor create() {
    return new CircuitBreakerBeanPostProcessor();
  }
}
