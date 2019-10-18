package com.minfengyu.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Tset1 {

  @Test
  public void test1(){

    try {
      throw new RuntimeException("nihao");
    } catch (RuntimeException e) {

    }
    System.out.println(1);
  }
  @Test
  public void test2(){
    try {
      HashSet<String> set = new HashSet<>();
      set.add("Nha");
      set.add("Nha2");
      set.add("Nha4");
      ObjectMapper omp = new ObjectMapper();
      String string = omp.writeValueAsString(set);
      System.out.println(string);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test3(){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String format1 = format.format(new Date());
    System.out.println(format1);
  }
  @Test
  public void test4(){

    Game g1 = new Game("你好", 1);
    Game g2 = new Game("你好", 1);

    System.out.println(g1.equals(g2));
  }
}
