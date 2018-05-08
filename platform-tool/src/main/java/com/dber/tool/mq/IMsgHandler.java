package com.dber.tool.mq;

/**
 * <li>文件名称: IMsgHandler.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月23日
 * @author dev-v
 */
public interface IMsgHandler<M> {

	/**
	 * <pre>
	 * 消息处理
	 * </pre>
	 * 
	 * @param msg
	 */
	void onMessage(M msg);
}
