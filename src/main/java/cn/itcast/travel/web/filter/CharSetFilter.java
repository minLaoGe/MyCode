package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CharSetFilter implements Filter {
  public void destroy() {

  }

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    String uri = request.getRequestURI();
    request.setCharacterEncoding("utf-8");
    if (uri.contains("/index.jsp")||uri.contains("LoginServlet")||uri.contains("/css")||uri.contains("/js")||uri.contains("/CheckImageServlet")){
      chain.doFilter(request,response);
    }else {
      response.setContentType("text/html;charset=utf-8");
      chain.doFilter(request, response);
    }


  }

  public void init(FilterConfig config) throws ServletException {

  }

}
