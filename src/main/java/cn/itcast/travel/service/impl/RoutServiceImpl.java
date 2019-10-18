package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RoutService;

import java.util.List;

public class RoutServiceImpl implements RoutService {
  private RouteDao rd=new RouteDaoImpl();
  @Override
  public PageBean<Route> getPageBeanByCid(int cid,int currentPage,int pageSize,String rname) {
    PageBean<Route> rpb = new PageBean<>();
    int statr=(currentPage-1)*pageSize;
    List<Route> list = rd.findAllRouteByPage(cid,statr,pageSize,rname);
    int totoalResignCount=rd.findTotalCount(cid,rname);
    int totoalPage=0;
    rpb.setList(list);
    rpb.setCurrentPage(currentPage);
    rpb.setPageSize(pageSize);
    rpb.setTotoalResignCount(totoalResignCount);
    if (totoalResignCount%pageSize==0){
       totoalPage=totoalResignCount/pageSize;
    }else {
       totoalPage=totoalResignCount/pageSize+1;
    }
    rpb.setTotoalPage(totoalPage);
    return rpb;
  }

  @Override
  public Route getRouteByRid(int rid) {
    return rd.findOneRouteByRid(rid);
  }

  @Override
  public Seller getSellerBySid(int sid) {

    return rd.findOneSellerBySid(sid);
  }

  @Override
  public List<RouteImg> getListRouteImgByRid(int rid) {
    return rd.findlistRouteImgByRid(rid);
  }

  @Override
  public Category getCategoryBySid(int cid) {

    return  rd.getCategoryBySid(cid);
  }

  @Override
  public boolean getFavoriteByCidAnRid(int rid, User user) {
    Favorite fbya = rd.getFavoriteByCidAndRid(rid, user.getUid());
    if (fbya!=null){
      return true ;
    }else {
      return false;
    }

  }

  @Override
  public int addCountOfFavorite(int rid, int uid) {
    int i = rd.addCountOfFavoriteInTabFavorite(rid, uid);
    if (i!=0){
      rd.addCountOfFavoriteInTabRoute(rid);
      return 1;
    }else {
            return 0;

    }


  }

  @Override
  public boolean deleteFavoriteByRidAndUser(int rid, User alLoginUser) {

    return rd.deleteFavoriteByRidAndUid(rid,alLoginUser.getUid());
  }
}
