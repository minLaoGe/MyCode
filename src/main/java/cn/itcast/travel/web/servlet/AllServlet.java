package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class AllServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {

      String uri = req.getRequestURI();
      String mendthod = uri.substring(uri.lastIndexOf("/")+1);
      Method method = this.getClass().getMethod(mendthod,HttpServletRequest.class,HttpServletResponse.class);
      method.invoke(this, req, resp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  protected String writeForString(Object obj){
    try {
      ObjectMapper map = new ObjectMapper();
      String string = map.writeValueAsString(obj);
      return string;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
  protected void writeForStringAndPrint(HttpServletResponse response,Object obj){
    try {
      response.setContentType("application/json;charset=utf-8");
      ObjectMapper map = new ObjectMapper();
      map.writeValue(response.getOutputStream(),obj);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
