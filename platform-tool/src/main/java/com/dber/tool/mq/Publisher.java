package com.dber.tool.mq;

/**
 * <li>文件名称: Producer.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月23日
 * @author dev-v
 */
public interface Publisher {
	/**
	 * <pre>
	 * 发布消息
	 * </pre>
	 * 
	 * @param key
	 * @param object
	 */
	void pub(String key, Object object);
}
