package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
  private Integer currentPage;
  private Integer pageSize;
  private Integer totoalPage;
  private Integer totoalResignCount;
  private List<T> list;//每页显示的数据集合

  @Override
  public String toString() {
    return "PageBean{" +
      "currentPage=" + currentPage +
      ", pageSize=" + pageSize +
      ", totoalPage=" + totoalPage +
      ", totoalResignCount=" + totoalResignCount +
      ", list=" + list +
      '}';
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getTotoalPage() {
    return totoalPage;
  }

  public void setTotoalPage(Integer totoalPage) {
    this.totoalPage = totoalPage;
  }

  public Integer getTotoalResignCount() {
    return totoalResignCount;
  }

  public void setTotoalResignCount(Integer totoalResignCount) {
    this.totoalResignCount = totoalResignCount;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public PageBean() {
  }

  public PageBean(Integer currentPage, Integer pageSize, Integer totoalPage, Integer totoalResignCount, List<T> list) {
    this.currentPage = currentPage;
    this.pageSize = pageSize;
    this.totoalPage = totoalPage;
    this.totoalResignCount = totoalResignCount;
    this.list = list;
  }
}
