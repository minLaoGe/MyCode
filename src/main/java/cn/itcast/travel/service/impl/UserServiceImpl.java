package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import sun.misc.Request;

public class UserServiceImpl implements UserService {
  private UserDao userDao = new UserDaoImpl();
  private ResultInfo rif = new ResultInfo();

  @Override
  public ResultInfo registerUser(User user,String path) {
    if (userDao.ifExistsUser(user.getUsername())) {
      rif.setErrorMsg("客户名已被注册");
      rif.setFlag(false);

    } else {
      if (userDao.ifExistEmail(user.getEmail())) {
        rif.setErrorMsg("邮箱已被注册");
        rif.setFlag(false);

      } else {
        rif.setFlag(true);

        String uid = UuidUtil.getUuid();
        user.setCode(uid);
        user.setStatus("N");
        userDao.addUser(user);
        String email = user.getEmail();
        String context = "<a href=\"http://192.168.122.130:8080/"+path+"user/activeRequest?uuid=" + uid + "\">点我激活邮件</a>";
        MailUtils.sendMail(email, context, "激活邮件");
      }
    }
    return rif;
  }

  @Override
  public boolean activeUser(String code) {
    User user = userDao.findUserByCode(code);
    if (user.getCode().equals(code)) {
      userDao.updateStatus("Y");
      return true;
    }
    return false;
  }

  @Override
  public void deleteUserByCode(String code) {
    userDao.deleteUserByCode(code);
  }

  @Override
  public User login(User user) {
    User user2 = userDao.findUserByUsernamePassWord(user.getUsername(), user.getPassword());
    if (user2 != null) {
      return user2;
    }
    return null;
  }
}
