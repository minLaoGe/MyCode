package cn.itcast.travel.service;

import cn.itcast.travel.domain.*;

import java.util.List;

public interface RoutService {
   PageBean<Route> getPageBeanByCid(int cid,int currentPage,int pageSize,String rname);

   Route getRouteByRid(int rid);

   Seller getSellerBySid(int sid);

   List<RouteImg> getListRouteImgByRid(int rid);

   Category getCategoryBySid(int cid);

   boolean getFavoriteByCidAnRid(int rid, User user);

   int addCountOfFavorite(int rid, int uid);

   boolean deleteFavoriteByRidAndUser(int rid, User alLoginUser);
}
