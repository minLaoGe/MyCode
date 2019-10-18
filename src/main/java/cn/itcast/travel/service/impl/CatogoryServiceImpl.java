package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CatogoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CatogoryServiceImpl implements CatogoryService {
    private CategoryDao cgo=new CategoryDaoImpl();

  @Override
  public List<Category> findAllCatogory() {
    Jedis jedis = JedisUtil.getJedis();
    Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
    List<Category> list=null;
    if (category==null||category.size()==0){
      System.out.println("从数据库中查");
      list=cgo.findAllCatogory();
      for (int i = 0; i < list.size(); i++) {
        jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());

      }

    }else {
      System.out.println("从redis中查");
      list=new ArrayList<Category>();
      for (Tuple s : category) {
        Category category1 = new Category();
        category1.setCname(s.getElement());
        category1.setCid((int) s.getScore());
        list.add(category1);
      }
    }
    jedis.close();
    return list;
  }
}
