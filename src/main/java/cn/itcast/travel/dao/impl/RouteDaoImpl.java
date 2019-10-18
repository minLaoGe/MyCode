package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
  private JdbcTemplate jtl = new JdbcTemplate(JDBCUtils.getDataSource());
  @Override
  public List<Route> findAllRouteByPage(int cid,int currentPage,int pageSize,String rname) {
    StringBuilder sb = new StringBuilder(" select * from tab_route where 1 = 1  ");
    List list=new ArrayList();
    if (cid>0){
      sb.append(" and cid = ? ");
      list.add(cid);
    }

    if (rname!=null&&!"".equals(rname)&&!"null".equals(rname)){
      sb.append(" and rname like ? ");
      list.add("%"+rname+"%");
    }
    sb.append(" limit ?,?");
    list.add(currentPage);
    list.add(pageSize);
    String sql = sb.toString();
    List<Route> query = jtl.query(sql, new BeanPropertyRowMapper<Route>(Route.class), list.toArray());
    return query;
  }

  @Override
  public int findTotalCount(int cid,String rname) {
    StringBuilder sb = new StringBuilder("select count(*) from tab_route where 1=1 and cid = ? ");
    List list = new ArrayList();
    list.add(cid);
    if (rname!=null&&!"".equals(rname)&&!"null".equals(rname)){
      sb.append(" and rname like ?");
      list.add("%"+rname+"%");
    }
    String sql = sb.toString();
    Integer integer = jtl.queryForObject(sql, Integer.class, list.toArray());
    return integer;

  }

  @Override
  public Route findOneRouteByRid(int rid) {
    String sql="select * from tab_route where rid = ?";
    Route route = jtl.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    return route;
  }

  @Override
  public Seller findOneSellerBySid(int sid) {
    String sql="select * from tab_seller where sid = ? ";
    Seller seller = jtl.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
    return seller;
  }

  @Override
  public List<RouteImg> findlistRouteImgByRid(int rid) {
    String sql="select * from tab_route_img where rid = ?";
    List<RouteImg> query = jtl.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    return query;
  }

  @Override
  public Category getCategoryBySid(int cid) {
    try {
      String sql="select * from tab_category where cid= ?";
      Category category = jtl.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class), cid);
      return category;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public Favorite getFavoriteByCidAndRid(int rid, int uid) {
    try {
      String sql="select * from tab_favorite where rid = ? and uid = ?";
      Favorite query = jtl.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
      return query;
    }catch (Exception e){
      return null;
    }

  }

  @Override
  public int addCountOfFavoriteInTabFavorite(int rid, int uid) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String format1 = format.format(new Date());
      String sql="INSERT INTO tab_favorite VALUES(?,?,?)";
      jtl.update(sql,rid,format1,uid);
      return 1;
    } catch (Exception e) {
      return 0;
    }

  }

  @Override
  public void addCountOfFavoriteInTabRoute(int rid) {
    String sql="update tab_route set count=count+1 where rid= ? ";

    jtl.update(sql,rid);
  }

  @Override
  public boolean deleteFavoriteByRidAndUid(int rid, int uid) {
    try {
      String sql="update tab_route set count=count-1 where rid= ? ";
      jtl.update(sql,rid);
      String sql2="delete from tab_favorite where rid = ? and uid = ?";
      jtl.update(sql2,rid,uid);
      return true;
    } catch (DataAccessException e) {
      return false;
    }

  }
}
