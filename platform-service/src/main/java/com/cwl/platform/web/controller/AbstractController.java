package com.cwl.platform.web.controller;

import com.cwl.platform.entity.Page;
import com.cwl.platform.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019/9/30
 */
public class AbstractController<T, S extends IService<T>> {

  protected S service;

  protected AbstractController(S service) {
    this.service = service;
  }

  /**
   * <pre>
   * 若一定要使用 请务必加上limit字句
   * </pre>
   *
   * @return
   */
  @RequestMapping
  public List<T> query(T data) {
    return service.query(data);
  }

  @RequestMapping("/{key}")
  public T get(@PathVariable("key") Serializable id) {
    return service.get(id);
  }

  /**
   * 查询返回的数据只有一条
   *
   * @param data
   * @return
   */
  @RequestMapping("/getByCondition")
  public T getByCondition(T data) {
    return service.getByCondition(data);
  }

  /**
   * <pre>
   * 分页查询数据
   * 查询结果保存到分页对象中
   * </pre>
   *
   * @param page
   * @return
   */
  @RequestMapping("/page")
  public Page queryPage(Page<T> page) {
    service.queryPage(page);
    return page;
  }

  /**
   * <pre>
   * 根据条件获取主键集合
   * 最大返回1000条
   * </pre>
   *
   * @return
   */
  @RequestMapping("/getIds")
  public long[] getIds(T data) {
    throw new UnsupportedOperationException("暂时不支持！");
  }

  @RequestMapping("/has")
  public boolean has(T data) {
    return service.has(data);
  }

  /**
   * 根据查询条件获取满足条件的记录数量
   *
   * @param data
   * @return
   */
  public int count(T data) {
    return service.count(data);
  }

  /**
   * <pre>
   * 根据主键删除数据
   * </pre>
   *
   * @param key
   * @return 删除成功行数
   */
  @RequestMapping("/{key}/del")
  public int del(@PathVariable("key") Serializable key) {
    return service.del(key);
  }

  /**
   * 根据删除条件删除数据
   *
   * @param data
   * @return
   */
  @RequestMapping("/delByCondition")
  public int delByCondition(T data) {
    return service.delByCondition(data);
  }

  /**
   * <pre>
   * 保存数据 ：
   * 有id为修改
   * 无id为新增
   * </pre>
   *
   * @param data
   * @return
   */
  @RequestMapping("/save")
  public T save(T data) {
    service.save(data);
    return data;
  }

  @RequestMapping("/saves")
  public List<T> saves(List<T> data) {
    service.saves(data);
    return data;
  }
}
