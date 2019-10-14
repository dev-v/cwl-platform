package com.cwl.tool.zk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
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
  CuratorFramework client;
  V v;
  String path;

  private static final Map<String, CuratorFramework> curatorFrameworks = new HashMap<>();


  public static final ZKNode getRootNodeInstance(ZKConfig config) {
    CuratorFramework client = curatorFrameworks.get(config.getHost());
    if (client == null) {
      client = CuratorFrameworkFactory.newClient(config.getHost(),
              new RetryForever(config.getRetryConnectionIntervalSeconds() * 1000));
      client.start();
      try {
        client.blockUntilConnected();
      } catch (InterruptedException e) {
        log.error(e);
        throw new IllegalStateException(e);
      }
      curatorFrameworks.put(config.getHost(), client);
    }
    return ZKUtil.saveNodeInstance(client, config.getRootPath(), null);
  }


  @Override
  public <CV> ZKNode<CV> save(String childPath, CV val) {
    ZKNodeImpl instance = ZKUtil.saveNodeInstance(client, path + childPath, val);
    instance.parent = this;
    return instance;
  }

  @Override
  public ZKNode save(String childPath) {
    return save(childPath, null);
  }

  @Override
  public void save(V val) {
    ZKUtil.saveNodeInstance(client, path, val);
    this.v = val;
  }

  @Override
  public void delete(String childPath) {
    ZKUtil.delete(client, path + childPath);
  }

  @Override
  public void delete() {
    ZKUtil.delete(client, path);
  }

  @Override
  public boolean exist(String childPath) {
    return ZKUtil.exist(client, path + childPath);
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
      client.create().withMode(CreateMode.EPHEMERAL).forPath(this.path + path);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
