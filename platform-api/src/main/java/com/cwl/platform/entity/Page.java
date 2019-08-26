package com.cwl.platform.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Collections;
import java.util.List;

/**
 * <li>文件名称: Page.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月20日
 */
public class Page<E> {

  public static final int MAX_PAGE_SIZE = 30_000;

  /**
   * 当前页，从1开始
   */
  private int currentPage;

  /**
   * 每页数量
   */
  private int pageSize;

  /**
   * 总数据行数
   */
  private int count;

  /**
   * 总页数
   */
  private int allPage;

  /**
   * 当前页查询的数据
   */
  private List<E> datas;

  /**
   * 查询条件
   */
  @JSONField(serialize = false)
  private Object condition;

  /**
   * 排序字段
   */
  @JSONField(serialize = false)
  private String sort;

  /**
   * @param currentPage 当前页 从1开始
   * @param pageSize    当前页行数
   */
  public Page(int currentPage, int pageSize) {
    this.currentPage = currentPage > 0 ? currentPage : 1;
    this.pageSize = pageSize > 0 ? pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize : 1;
  }

  /**
   * pageSize 默认20
   *
   * @param currentPage
   * @see Page#Page(long, long)
   */
  public Page(int currentPage) {
    this(currentPage, 10);
  }

  /**
   * <pre>
   * currentPage 默认1
   * </pre>
   *
   * @see Page#Page(long)
   */
  public Page() {
    this(1);
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count > -1 ? count : 0;
    int temp = count % pageSize;
    allPage = (count / pageSize) + (temp == 0 ? 0 : 1);
    if (allPage < 1) {
      currentPage = 1;
    } else if (currentPage > allPage)
      currentPage = allPage;
  }

  public int getAllPage() {
    return allPage;
  }

  public List<E> getDatas() {
    return datas;
  }

  public void setDatas(List<E> datas) {
    this.datas = datas;
  }

  public Object getCondition() {
    return condition;
  }

  public void setCondition(Object condition) {
    this.condition = condition;
  }

  public int getTotal() {
    return count;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  @SuppressWarnings("unchecked")
  public void emptyDatas() {
    this.datas = Collections.EMPTY_LIST;
  }
}
