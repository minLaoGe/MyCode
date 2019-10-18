package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl implements UserDao {
  private JdbcTemplate jtp = new JdbcTemplate(JDBCUtils.getDataSource());

  @Override
  public void addUser(User user) {
    String sql = "insert into tab_user values(?,?,?,?,?,?,?,?,?,?)";
    int update = jtp.update(sql, null, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
  }

  @Override
  public boolean ifExistsUser(String username) {

    try {
      String sql = "select * from tab_user where username=? ";
      User query = jtp.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  @Override
  public boolean ifExistEmail(String email) {
    try {
      String sql = "select * from tab_user where email=? ";
      User query = jtp.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email);
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  @Override
  public String getUuid(String username) {
    String sql = "select * from tab_user where username=?";
    User user = jtp.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class));

    return user.getCode();
  }

  @Override
  public void updateStatus(String y) {
    String sql = "update tab_user set status=?";
    jtp.update(sql, y);
  }

  @Override
  public User findUserByCode(String code) {
    String sql = "select * from tab_user where code=?";
    User user = jtp.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
    return user;
  }

  @Override
  public void deleteUserByCode(String code) {
    String sql = "delete from tab_user where code=?";
    jtp.update(sql, code);
  }

  @Override
  public User findUserByUsernamePassWord(String username, String password) {
    try {
      String sql="select * from tab_user where username=? and password=?";
      User user = jtp.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
      return user;
    } catch (Exception e) {
      return null;
    }
  }

}
