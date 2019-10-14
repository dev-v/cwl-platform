package com.cwl.tool.zk;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019/10/14
 */
public class ZKUtil {
  private static final Log log = LogFactory.getLog(ZKUtil.class);

  public static <D> ZKNodeImpl<D> saveNodeInstance(CuratorFramework client, String path, D data) {
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
      nodeInstance.client = client;
      nodeInstance.path = path;
      nodeInstance.v = data;
      return nodeInstance;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public static void delete(CuratorFramework client, String path) {
    try {
      client.delete().deletingChildrenIfNeeded().forPath(path);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public static boolean exist(CuratorFramework client, String path) {
    try {
      return client.checkExists().forPath(path) != null;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public static List<String> children(CuratorFramework client, String path) {
    try {
      return client.getChildren().forPath(path);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
