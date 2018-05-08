package com.dber.platform.service;

import com.dber.platform.mapper.IMapper;
import com.dber.platform.entity.Page;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * <li>文件名称: AbstraService.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月19日
 */
public abstract class AbstractService<E> implements IService<E> {

  IMapper<E> mapper;

  @Override
  public int insert(E e) {
    return mapper.insert(e);
  }

  @Override
  public int del(long key) {
    return mapper.del(key);
  }

  @Override
  public int update(E e) {
    return mapper.update(e);
  }

  @Override
  public int save(E e) {
    return mapper.save(e);
  }

  @Override
  public E get(long key) {
    return mapper.get(key);
  }

  @Override
  public Collection<E> gets(long[] keys) {
    return mapper.gets(keys);
  }

  @Override
  public void queryPage(Page<E> page) {
    mapper.queryPage(page);
  }

  @Override
  public Collection<E> query(E e) {
    return mapper.query(e);
  }

  @Override
  public E getByCondition(E e) {
    return mapper.getByCondition(e);
  }

  @Override
  public boolean has(E e) {
    return mapper.has(e) != null;
  }

  public long[] getIds(E e) {
    return mapper.getIds(e);
  }

  @Override
  public int dels(long[] keys) {
    return mapper.dels(keys);
  }

  @Override
  public int delByCondition(E e) {
    return mapper.delByCondition(e);
  }

  @Override
  public int count(E e) {
    return mapper.count(e);
  }

  /**
   * <pre>
   * 返回操作实体 < E > 的纯mapper对象
   * </pre>
   *
   * @return
   */
  protected abstract IMapper<E> getMapper();

  @PostConstruct
  public final void setMapper() {
    if (this.mapper == null) {
      this.mapper = getMapper();
    }
  }
}
