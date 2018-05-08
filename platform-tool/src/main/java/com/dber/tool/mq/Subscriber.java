package com.dber.tool.mq;

/**
 * <li>文件名称: Subscriber.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月23日
 * @author dev-v
 */
public interface Subscriber {
	/**
	 * <pre>
	 * 订阅消息
	 * 支持key和pattern的格式
	 * 完全匹配直接使用:"keyName"
	 * 模式匹配参数格式为:{pattern}
	 * </pre>
	 * 
	 * @param key
	 * @param handler
	 */
	<M> void sub(IMsgHandler<M> handler, String... keyOrPattern);
}
