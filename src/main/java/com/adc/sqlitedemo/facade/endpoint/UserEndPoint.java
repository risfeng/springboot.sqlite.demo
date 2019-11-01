package com.adc.sqlitedemo.facade.endpoint;

import com.adc.sqlitedemo.common.response.HttpResult;
import com.adc.sqlitedemo.repository.entity.User;
import com.adc.sqlitedemo.repository.entity.UserExample;
import com.adc.sqlitedemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务接口
 *
 * @author risfeng
 * @date 2019/11/02
 */
@Slf4j
@RestController
@RequestMapping(path = "api/v1/users")
@Api(value = "用户服务接口", tags = {"用户服务接口"})
public class UserEndPoint {

  private final UserService userService;

  @Autowired
  public UserEndPoint(UserService userService) {
    this.userService = userService;
  }

  /**
   * 获取用户
   */
  @ApiOperation(value = "获取用户")
  @GetMapping
  public HttpResult<List<User>> getUsers() {
    UserExample example = new UserExample();
    example.createCriteria()
        .andCreatedAtGreaterThanOrEqualTo("2019-11-01");
    return HttpResult.returnResult(() -> userService.selectByExample(example), log);
  }

}
