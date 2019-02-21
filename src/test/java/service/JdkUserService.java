package service;

import org.springframework.stereotype.Service;

/**
 * @author liuxin
 * @version Id: UserService.java, v 0.1 2019-02-21 10:15
 */
@Service
public interface JdkUserService {

  String getUserDesc(String name);
}
