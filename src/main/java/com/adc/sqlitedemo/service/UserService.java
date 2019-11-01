package com.adc.sqlitedemo.service;

import com.adc.sqlitedemo.repository.entity.User;
import com.adc.sqlitedemo.repository.entity.UserExample;
import java.util.List;

/**
 * ${description}
 *
 * @author risfeng
 * @date 2019/11/02
 */
public interface UserService {


  long countByExample(UserExample example);

  int deleteByExample(UserExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(User record);

  int insertSelective(User record);

  List<User> selectByExample(UserExample example);

  User selectByPrimaryKey(Integer id);

  int updateByExampleSelective(User record, UserExample example);

  int updateByExample(User record, UserExample example);

  int updateByPrimaryKeySelective(User record);

  int updateByPrimaryKey(User record);

}
