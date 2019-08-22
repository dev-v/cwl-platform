package com.cwl.tool.mq;

import java.util.HashMap;
import java.util.Map;

/**
 * <li>文件名称: AbstractMqManager.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: 提供对系统publisher和subscribe的管理</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月23日
 * @author dev-v
 */
public abstract class AbstractMqManager implements IMqManager {

	// 已经生成的系统发布者
	private static final Map<String, Publisher> system_publisher = new HashMap<>();

	private static final Map<String, Subscriber> system_subscriber = new HashMap<>();

	@Override
	public Publisher getPublisher(String system) {
		system = system.trim();
		Publisher publisher = system_publisher.get(system);
		if (publisher == null) {
			publisher = sgetPublisher(system);
			system_publisher.put(system, publisher);
		}
		return publisher;
	}

	@Override
	public Subscriber getSubscirber(String system) {
		system = system.trim();
		Subscriber subscriber = system_subscriber.get(system);
		if (subscriber == null) {
			subscriber = sgetSubscriber(system);
			system_subscriber.put(system, subscriber);
		}
		return subscriber;
	}

	/**
	 * <pre>
	 * 产生真实的publisher
	 * </pre>
	 * 
	 * @param system
	 * @return
	 */
	protected abstract Publisher sgetPublisher(String system);

	/**
	 * 
	 * <pre>
	 * 产生真实的subscriber
	 * </pre>
	 * 
	 * @param system
	 * @return
	 */
	protected abstract Subscriber sgetSubscriber(String system);

}
