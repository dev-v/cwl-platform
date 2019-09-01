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
  String FIELD_NAME = "deserializationType";

  static Object toObject(Object object) throws ClassNotFoundException {
    if (object == null) {
      return null;
    } else if (object instanceof IGenericSerializationType) {
      return object;
    } else if (object instanceof JSONObject) {
      return JSON.parseObject(((JSONObject) object).toJSONString(), Class.forName(((JSONObject) object).getString(FIELD_NAME)));
    } else if (object instanceof String) {
      return JSON.parseObject((String) object, Class.forName(JSON.parseObject((String) object).getString(FIELD_NAME)));
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * 序列化反序列化时，用于自动识别类型
   *
   * @return
   */
  Class getDeserializationType();
}
