
![](http://img.springlearn.cn/9e80ee8120efa5c73b9e7ee99c36b4c1.jpg)

在现代微服务框架中如: SpringCloud,为了阻止服务雪崩,引入了一种熔断的措施,
即: 为可能出现错误的接口方法,提供一种备用方法,当出现错误就执行备用方法来返回结果。
小编认为这种方法是非常优雅的,虽然这种逻辑,也可以在方法中通过代码逻辑来实现,
但是熔断的处理方式会更加简化这种的处理,使我们的熔断方法看起来更加的优化。

`Turnoff` 就是简化代码而产生的,通过类似于`Hystrix`的注解方法,来实现相同的功能,`Turnoff`主要是利用`BeanPostProcessor`后置处理器来完成,代码非常简单,只依赖`Spring`,无论是`Dubbo分布式`还是`SpringCloud分布式`,还是单体应用都可以使用。


## 目录

- 配置方法
    - SpringBoot自动化配置
    - Spring配置
- 使用方法
    - `TurnoffCommand`代码展示
- 实现原理
    - BeanPostProcessor初始化后置处理器
    - Aop切面代理
    - 自动化配置

### 配置方法

#### SpringBoot自动化配置

```
  <!--熔断-->
        <dependency>
            <groupId>org.turnoff.spring.boot</groupId>
            <artifactId>turnoff-spring-boot-starter</artifactId>
            <version>1.0.1</version>
        </dependency>
```

#### Spring配置

```
<bean class="org.turnoff.processor.CircuitBreakerBeanPostProcessor">
</bean>
```

### 使用方法
#### `TurnoffCommand`代码展示

```
@Service
public class UserServiceImpl  {


  @TurnoffCommand(fallbackMethod = "getBreakUserName")
  public String getUserName(String name) {
    throw new RuntimeException();
  }

  public String getBreakUserName(String name) {
    return "Mock用户id:" + name;
  }

}
```

### 实现原理

#### BeanPostProcessor初始化后置处理器

`CircuitBreakerBeanPostProcessor`实现了`BeanPostProcessor`的初始化后置处理器,和`Ordered`优先级最低执行。当Spring中bean在完成创建最后为其生成代理对象,在代理对象中执行`TurnoffCommand`逻辑。此方法不会影响Spring管控的Bean的生命周期。

#### Aop切面代理

`TurnoffProxyFactory`负责生成代理对象,代理分为Jdk代理和Cglib代理,这部分主要利用Spring中自带工具`ProxyFactory`来创建。此部分逻辑在小编的头条号上存在。
- [Springframework-aop学习（一）](https://www.toutiao.com/i6581728837325816327/)
- [Springframework-aop学习（二）](https://www.toutiao.com/i6582105757242622468/)


#### 自动化配置
所谓自动化配置是利用SpringBoot的autoconfigure进行实现。之前写过详细的文章来实战SpringBoot的自动化配置,想仔细了解的,可以参考下文。
[SpringBoot可插拔开箱即用之组件开发](https://blog.springlearn.cn/posts/64919/)