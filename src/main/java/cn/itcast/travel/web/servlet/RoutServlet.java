package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RoutService;
import cn.itcast.travel.service.impl.RoutServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/rout/*")
public class RoutServlet extends AllServlet {

  private RoutService rs = new RoutServiceImpl();

  public void routPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //获取cid值
    String cidStr = request.getParameter("cid");
    int cid = 0;

    //获取每页的显示的个数值
    String pageSizeStr = request.getParameter("pageSize");
    int pageSize = 0;

    //获取分页查询的条件
    String rname = request.getParameter("rname");
    if (rname != null && !"".equals(rname)) {
      byte[] bytes = rname.getBytes("iso-8859-1");
      rname = new String(bytes, "utf-8");
    }

    //获取request中的currentPage值
    String currentPageStr = request.getParameter("currentPage");

    int currentPage = 0;


    //对cidStr的值进行非空判断
    if (cidStr != null && !"".equals(cidStr)) {

      //将字符串的cidStr转化为int类型的cid
      cid = Integer.parseInt(cidStr);

      //判读cid是否不为5是的话就将cid等于5
      if (cid != 5) {
        cid = 5;
      }

      //如果cidStr为空的话,则令cid为5
    } else {
      cid = 5;
    }


    //对request中的值进行非空判断,防止恶意传参
    if (currentPageStr != null && !"".equals(currentPageStr)) {

      //将字符类型的当前页转化为Int类型
      currentPage = Integer.parseInt(currentPageStr);
      Cookie cookie = new Cookie("currentePage", String.valueOf(currentPage));
      //如果当前页小于1时,将当前页的值设为1
      if (currentPage < 1) {
        currentPage = 1;
      }

      //如果request中的当前页为空时,则将当前页的值设为1;
    } else {
      currentPage = 1;
    }


    //对一页显示的信息条数进行非空判断
    if (pageSizeStr != null && !"".equals(pageSizeStr)) {

      //如果不为空则将信息条数转化为int类型,赋值给pageSize
      pageSize = Integer.parseInt(pageSizeStr);

      //如果信息条数小于3的话,则将信息条数设置为3
      if (pageSize <= 3) {
        pageSize = 3;
      }

      //如果每条信息的条数为空的话,则将信息条数设置为5
    } else {
      pageSize = 5;
    }

    //根据给的cid和当前页数和信息条数去得到分页的信息,和数据
    PageBean<Route> pageBeanByCid = rs.getPageBeanByCid(cid, currentPage, pageSize, rname);

    //将pageBean对象序列化JSON数据并反馈给客户端
    writeForStringAndPrint(response, pageBeanByCid);
  }

  public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    //现获取rid的值根据rid值去找用户的信息,并获取cid的信息,
    String ridStr = request.getParameter("rid");

    //用来存入rid的数值
    int rid = 0;

    //非空判断
    if (ridStr != null && !"".equals(ridStr)) {

      //将Str的值转化为Int类型
      rid = Integer.parseInt(ridStr);
    }

    //非逸出判断
    if (rid <= 0) {
      rid = 1;
    }

    //根据dao层去得到Route对象
    Route rt = rs.getRouteByRid(rid);

    //获取商家信息id
    int sid = rt.getSid();
    int cid = rt.getCid();

    //根据rid去获取img对象
    List<RouteImg> routeImgList = rs.getListRouteImgByRid(rid);

    //根据获取的厂商id,去获取商家对象
    Seller seller = rs.getSellerBySid(sid);

    //根据产商的id,去获取商家的分类的信息
    Category cg = rs.getCategoryBySid(cid);

    //将获取到的对象全部封装到Route对象里面
    rt.setRouteImgList(routeImgList);
    rt.setSeller(seller);
    rt.setCategory(cg);

    //将Route对象到服务端
    this.writeForStringAndPrint(response, rt);

  }

  public void judgeFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = (User) request.getSession().getAttribute("alLoginUser");
    response.setContentType("application/json;charset=utf-8");
    boolean isFavorite = false;
    String ridStr = request.getParameter("rid");

    if (ridStr != null && !"".equals(ridStr) && user != null && !"".equals(user)) {
      int rid = Integer.parseInt(ridStr);
      isFavorite = rs.getFavoriteByCidAnRid(rid, user);
    }

    String json = "{\"isFavorite\":" + isFavorite + "}";
    System.out.println(json);
    response.getWriter().write(json);

//    rs.getFavoriteByRidAndUid()

  }

  public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String ridStr = request.getParameter("rid");
    response.setContentType("application/json;charset=utf-8");
    User user = (User) request.getSession().getAttribute("alLoginUser");
    if (ridStr != null && !"".equals(ridStr) && user != null && !"".equals(user)) {
      int rid = Integer.parseInt(ridStr);
      if (rid < 0) {
        rid = 0;
      }
      int value = rs.addCountOfFavorite(rid, user.getUid());
      if (value==0){
        return;
      }
      String isSuccess = "{\"isLogin\":" + true + "}";
      response.getWriter().write(isSuccess);
    } else {
      String isLogin = "{\"isLogin\":" + false + "}";

      response.getWriter().write(isLogin);

    }
  }
  public void cancelFavrite(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String ridStr = request.getParameter("rid");
    response.setContentType("application/json;charset=utf-8");
    User alLoginUser = (User)request.getSession().getAttribute("alLoginUser");
    if (ridStr!=null&&!"".equals(ridStr)&&alLoginUser!=null){
      int rid = Integer.parseInt(ridStr);
      if (rid<0){
        rid=1;
      }
      boolean ifDeleteFavo=rs.deleteFavoriteByRidAndUser(rid,alLoginUser);
      String ifDeleteFavoStr="{\"ifDeleteFavo\":"+ifDeleteFavo+"}";
      response.getWriter().write(ifDeleteFavoStr);
    }
  }


}
