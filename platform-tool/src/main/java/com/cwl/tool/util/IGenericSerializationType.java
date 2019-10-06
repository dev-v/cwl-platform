package com.cwl.tool.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * <pre>
 * 用于辅助泛型参数的虚拟化和反序列化
 * </pre>
 *
 * @author chenwl 2019-08-30
 */
public interface IGenericSerializationType {
  String FIELD_NAME = "zDeserializationType";

  static <E extends IGenericSerializationType> E toObject(Object object) {
    if (object == null) {
      return null;
    } else if (object instanceof IGenericSerializationType) {
      return (E) object;
    } else if (object instanceof JSONObject) {
      try {
        return (E) ((JSONObject) object).toJavaObject(Class.forName(((JSONObject) object).getString(FIELD_NAME)));
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    } else if (object instanceof String) {
      JSONObject json = JSON.parseObject((String) object);
      try {
        return (E) json.toJavaObject(Class.forName(json.getString(FIELD_NAME)));
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * 序列化反序列化时，用于自动识别类型
   *
   * @return
   */
  Class getZDeserializationType();
}
