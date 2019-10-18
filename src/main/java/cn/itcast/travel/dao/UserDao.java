package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

   void addUser(User user);
  boolean ifExistsUser(String username);
  boolean ifExistEmail(String email);

  String getUuid(String username);

  void updateStatus(String code);

  User findUserByCode(String code);

  void deleteUserByCode(String code);

  User findUserByUsernamePassWord(String username, String password);
}
