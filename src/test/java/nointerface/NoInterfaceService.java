package nointerface;

import org.spring.processor.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

import javax.xml.ws.Action;

/**
 * @author liuxin
 * @version Id: NoInterfaceService.java, v 0.1 2019-02-21 14:35
 */
@Component
public class NoInterfaceService {


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

  public static void main(String[] args) {

  }
}
