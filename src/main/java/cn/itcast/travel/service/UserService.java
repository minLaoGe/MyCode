package cn.itcast.travel.service;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;

public interface UserService {
  ResultInfo registerUser(User user,String path);


  boolean activeUser(String code);

  void deleteUserByCode(String code);

  User login(User user);

}
