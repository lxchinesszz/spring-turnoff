package service;

import org.spring.processor.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

/**
 * @author liuxin
 * @version Id: UserServiceImpl.java, v 0.1 2019-02-20 18:14
 */
@Service
public class JdkUserServiceImpl implements JdkUserService {

  @Action
  @HystrixCommand(fallbackMethod = "getBreakUserDesc")
  public String getUserDesc(String name) {
//    throw new RuntimeException();
    return "系统:" + name;
  }

  public String getBreakUserDesc(String name) {
//    throw new RuntimeException();
    return "HystrixCommand系统:" + name;
  }
}
