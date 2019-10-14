package com.cwl.tool.zk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019/10/14
 */
public class ZKNodeImpl<V> implements ZKNode<V> {

  private static final Log log = LogFactory.getLog(ZKNodeImpl.class);
  ZKNode parent;
  ZKNodeFactory factory;
  V v;
  String path;

  private static final Map<String, CuratorFramework> curatorFrameworks = new HashMap<>();


  @Override
  public <CV> ZKNode<CV> save(String childPath, CV val) {
    ZKNodeImpl instance = factory.saveNodeInstance(path + childPath, val);
    instance.parent = this;
    return instance;
  }

  @Override
  public ZKNode save(String childPath) {
    return save(childPath, null);
  }

  @Override
  public void save(V val) {
    factory.saveNodeInstance(path, val);
    this.v = val;
  }

  @Override
  public void delete(String childPath) {
    factory.delete(path + childPath);
  }

  @Override
  public void delete() {
    factory.delete(path);
  }

  @Override
  public boolean exist(String childPath) {
    return factory.exist(path + childPath);
  }

  @Override
  public V val() {
    return v;
  }

  @Override
  public String path() {
    return path;
  }

  @Override
  public ZKNode parent() {
    return parent;
  }

  @Override
  public boolean createEphemeralNode(String path) {
    try {
      factory.client.create().withMode(CreateMode.EPHEMERAL).forPath(this.path + path);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
