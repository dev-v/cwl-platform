package com.cwl.tool.zk;

import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019/10/14
 */
public class ZKNodeImplTest {
  ZKConfig config = new ZKConfig();

  public ZKNodeImplTest() {
    config.setHost("localhost:2181");
    config.setRootPath("/test");
  }


  @Test
  public void getRootNodeInstance() {
    ZKNode ZKNode = ZKNodeImpl.getRootNodeInstance(config);
    System.out.println(ZKNode);
  }


  public ZKNode save(String childPath, Object val) {
    return null;
  }

  public ZKNode save(String childPath) {
    return null;
  }

  public void save(Object val) {

  }

  public void delete(String childPath) {

  }

  public void delete() {

  }

  public boolean exist(String childPath) {
    return false;
  }


  public Object val() {
    return null;
  }

  public String path() {
    return null;
  }

  public ZKNode parent() {
    return null;
  }

  @Test
  public void createEphemeralNode() {
    boolean res = ZKNodeImpl.getRootNodeInstance(config).createEphemeralNode("/tmp1");
    System.out.println(res);
  }

}
