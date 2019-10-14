package com.cwl.tool.zk;

/**
 * <pre>
 * 一个zk节点实例
 * </pre>
 *
 * @author chenwl 2019/10/14
 */
public interface ZKNode<V> {

  /**
   * 保存一个子节点，并返回节点实例
   *
   * @param childPath
   * @param val
   * @return
   */
  <CV> ZKNode<CV> save(String childPath, CV val);

  /**
   * 保存一个子节点，并返回节点实例,不含数据
   *
   * @param childPath
   * @return
   */
  ZKNode save(String childPath);

  /**
   * 修改当前节点的值
   *
   * @param val
   */
  void save(V val);

  /**
   * 删除一个子节点
   *
   * @param childPath
   * @return
   */
  void delete(String childPath);

  /**
   * 删除自身
   *
   * @return
   */
  void delete();

  /**
   * 检查子节点是否存在
   *
   * @param childPath
   * @return
   */
  boolean exist(String childPath);

//  /**
//   * 获取子节点列表
//   */
//  List<String> children();
//
//  /**
//   * 获取子节点实例
//   *
//   * @param childPath
//   */
//  <CV> NodeInstance<CV> child(String childPath, Class<CV> clz);
//
//  /**
//   * 获取子节点实例
//   *
//   * @param childPath
//   */
//  <CV> CV childVal(String childPath, Class<CV> clz);
//
//  /**
//   * 获取子节点实例
//   *
//   * @param childPath
//   */
//  NodeInstance child(String childPath);

  /**
   * 获取当前节点数据
   */
  V val();

  /**
   * 当前节点的路径
   *
   * @return
   */
  String path();

  /**
   * 父节点
   *
   * @return
   */
  ZKNode parent();

  boolean createEphemeralNode(String path);
}
