package cn.itcast.travel.dao;

import cn.itcast.travel.domain.*;

import java.util.List;

public interface RouteDao {
  List<Route> findAllRouteByPage(int cid,int currentPage,int pageSize,String rname);

  int findTotalCount(int cid,String rname);

  Route findOneRouteByRid(int rid);

  Seller findOneSellerBySid(int sid);

  List<RouteImg> findlistRouteImgByRid(int rid);

  Category getCategoryBySid(int cid);

  Favorite getFavoriteByCidAndRid(int rid, int uid);

  int addCountOfFavoriteInTabFavorite(int rid, int uid);

  void addCountOfFavoriteInTabRoute(int rid);

  boolean deleteFavoriteByRidAndUid(int rid, int uid);
}
