package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
@WebServlet("/user/*")
public class UserServlet extends AllServlet {
  /**
   * 激活用户
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void activeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String code = request.getParameter("uuid");
    response.setContentType("text/html;charset=utf-8");

    if (code != null && !"".equals(code)) {
      UserService userService = new UserServiceImpl();
      boolean b = userService.activeUser(code);
      if (b) {

        response.getWriter().write("<a href='login.html'>注册成功点击跳转登录页面</a>");

        return;
      } else {
        userService.deleteUserByCode(code);
        response.getWriter().write("<a href='register.html'>注册失败点击重新注册</a>");

      }
    }else {
      response.getWriter().write("<a href='login.html'>你的账号已经被激活,点我去登录</a>");
    }
  }

  /**
   * 头部退出
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getSession().removeAttribute("alLoginUser");
    response.getWriter().write("");
  }

  /**
   * 登录
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    PrintWriter print = response.getWriter();
    response.setContentType("application/json;charset=utf-8");
    try {

      Map<String, String[]> map = request.getParameterMap();
      User user = new User();
      BeanUtils.populate(user, map);
      String check_code = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
      String check = request.getParameter("check");
      request.getSession().removeAttribute("CHECKCODE_SERVER");
      if (check != null && !"".equals(check) && check.equalsIgnoreCase(check_code)) {
        if (user != null && !"".equals(user)) {
          UserService use = new UserServiceImpl();
          User flag = use.login(user);
          System.out.println(flag);
          if (flag != null) {
            if ("Y".equals(flag.getStatus())) {
              ObjectMapper omap = new ObjectMapper();
              String alLoginUser = omap.writeValueAsString(flag);
              request.getSession().setAttribute("alLoginUser", flag);
              print.write("{\"flag\":true,\"mess\":\"\"}");
            } else {
              print.write("{\"flag\":false,\"mess\":\"账号尚未激活\"}");
            }
          } else {
            print.write("{\"flag\":false,\"mess\":\"账号或密码错误\"}");
          }
        } else {
          print.write("{\"flag\":false,\"mess\":\"账号或密码错误\"}");
        }
      } else {
        print.write("{\"flag\":false,\"mess\":\"验证码错误\"}");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取头部的名字
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void nameHeader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json;charset=utf-8");
    User user = (User)request.getSession().getAttribute("alLoginUser");
    if (user!=null&&!"".equals(user)){
      ObjectMapper map = new ObjectMapper();
      map.writeValue(response.getOutputStream(),user);

    }else {
      response.getWriter().write("false");
    }
  }

  /**
   * 注册
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json;charset=utf-8");
    String check_code = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
    request.getSession().removeAttribute("CHECKCODE_SERVER");
    String check=null;
    if (check_code!=null&&!"".equals(check_code)){
      check = request.getParameter("check");
    }

    ResultInfo ri = new ResultInfo();
    if (check != null && !"".equals(check)) {
      if (check.equalsIgnoreCase(check_code)) {
        try {
          Map<String, String[]> map = request.getParameterMap();
          User user = new User();
          BeanUtils.populate(user, map);
          UserService userService = new UserServiceImpl();
          ResultInfo resultInfo = userService.registerUser(user,request.getContextPath());
          ObjectMapper omr = new ObjectMapper();
          String str = omr.writeValueAsString(resultInfo);
          response.getWriter().write(str);
          return;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    ri.setFlag(false);
    ri.setErrorMsg("验证码错误");
    ObjectMapper omr = new ObjectMapper();
    String str = omr.writeValueAsString(ri);
    response.getWriter().write(str);
    return;
  }

  }
