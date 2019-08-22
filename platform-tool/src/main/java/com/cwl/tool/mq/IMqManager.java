package com.cwl.tool.mq;

/**
 * <li>文件名称: MqManager.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月23日
 */
public interface IMqManager {

    /**
     * 消息发布者
     *
     * @param systemName 发送消息的系统名称
     * @return
     */
    Publisher getPublisher(String systemName);

    /**
     * 消息订阅者
     *
     * @param systemName 被订阅的系统名称
     * @return
     */
    Subscriber getSubscirber(String systemName);
}
