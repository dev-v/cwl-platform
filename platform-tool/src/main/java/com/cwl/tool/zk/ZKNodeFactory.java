package com.cwl.tool.zk;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019/10/14
 */
public class ZKNodeFactory {

  private static final Log log = LogFactory.getLog(ZKNodeFactory.class);

  final CuratorFramework client;

  public ZKNodeFactory(ZKConfig config) {
    client = CuratorFrameworkFactory.newClient(config.getHost(),
        new RetryForever(config.getRetryConnectionIntervalSeconds() * 1000));
    client.start();
    try {
      client.blockUntilConnected();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }


  public final ZKNode getRootNodeInstance(String rootPath) {
    return saveNodeInstance(rootPath, null);
  }

  <D> ZKNodeImpl<D> saveNodeInstance(String path, D data) {
    try {
      if (data == null) {
        client.createContainers(path);
      } else {
        Object res = client.checkExists().forPath(path);
        byte[] bytes = JSONObject.toJSONString(data).getBytes();
        if (res == null) {
          log.info("zookeeper-创建节点:" + path);
          client.create().creatingParentsIfNeeded().forPath(path, bytes);
        } else {
          log.info("zookeeper-重置节点:" + path);
          client.setData().forPath(path, bytes);
        }
      }
      ZKNodeImpl nodeInstance = new ZKNodeImpl();
      nodeInstance.factory = this;
      nodeInstance.path = path;
      nodeInstance.v = data;
      return nodeInstance;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  void delete(String path) {
    try {
      client.delete().deletingChildrenIfNeeded().forPath(path);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  boolean exist(String path) {
    try {
      return client.checkExists().forPath(path) != null;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  List<String> children(String path) {
    try {
      return client.getChildren().forPath(path);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}
