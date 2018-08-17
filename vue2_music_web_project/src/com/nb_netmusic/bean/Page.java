package com.nb_netmusic.bean;

public class Page {
  private int pageNum = 0;
  private int pageSize = 0;

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = (pageNum-1)*10;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
