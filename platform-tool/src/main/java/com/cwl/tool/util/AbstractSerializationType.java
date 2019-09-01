package com.cwl.tool.util;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019-08-30
 */
public class AbstractSerializationType implements IGenericSerializationType {
  /**
   * 序列化反序列化时，用于自动识别类型
   *
   * @return
   */
  public Class<?> getDeserializationType() {
    return this.getClass();
  }
}
