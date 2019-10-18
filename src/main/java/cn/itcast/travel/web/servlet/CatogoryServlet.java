package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CatogoryService;
import cn.itcast.travel.service.impl.CatogoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Catogory/*")
public class CatogoryServlet extends AllServlet {
  private CatogoryService cgs=new CatogoryServiceImpl();
  public void firstCatogory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json;charset=utf-8");
    ObjectMapper map = new ObjectMapper();
    List<Category> list = cgs.findAllCatogory();
    String string = map.writeValueAsString(list);
    response.getWriter().write(string);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doPost(request, response);
  }
}
