package com.cwl.platform.service;

import com.cwl.platform.entity.Page;
import com.cwl.platform.mapper.IMapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

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
public abstract class AbstractService<E, M extends IMapper<E>> implements IService<E> {

  protected M mapper;

  protected AbstractService(M mapper) {
    this.mapper = mapper;
  }

  @Override
  public int insert(E e) {
    return mapper.insert(e);
  }

  @Override
  public int del(Serializable key) {
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
  public E get(Serializable key) {
    return mapper.get(key);
  }

  @Override
  public List<E> gets(long[] keys) {
    return mapper.gets(keys);
  }

  @Override
  public List<E> queryPage(Page<E> page) {
    return mapper.queryPage(page);
  }

  @Override
  public List<E> query(E e) {
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

  private static final int maxSaveSize = 512;

  @Override
  @Transactional
  public int saves(List<E> es) {
    int start = 0, end = maxSaveSize, size = es.size(), count = 0;
    if (size > 0) {
      do {
        if (end >= size) {
          if (start == 0) {
            return mapper.saves(es);
          }
          count += mapper.saves(es.subList(start, size));
          break;
        } else {
          count += mapper.saves(es.subList(start, end));
          start = end;
          end += maxSaveSize;
        }
      } while (true);
    }
    return count;
  }
}
