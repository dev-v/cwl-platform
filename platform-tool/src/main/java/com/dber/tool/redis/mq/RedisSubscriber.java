package com.dber.tool.redis.mq;

import java.util.ArrayList;
import java.util.Collection;

import com.dber.tool.mq.IMsgHandler;
import com.dber.tool.mq.Subscriber;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * <li>文件名称: RedisConsumer.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月23日
 * @author dev-v
 */
public class RedisSubscriber implements Subscriber {
	private static Log log = LogFactory.getLog(RedisSubscriber.class);

	private RedisMessageListenerContainer container;

	private String systemKey;

	protected RedisSubscriber(String systemKey, RedisMessageListenerContainer container) {
		this.container = container;
		this.systemKey = systemKey;
	}

	@Override
	public <M> void sub(IMsgHandler<M> handler, String... keys) {
		Collection<Topic> topics = new ArrayList<>();

		for (String key : keys) {
			key = key.trim();
			if (key.startsWith("{")) {
				key = key.substring(1, key.length() - 1).trim();
				topics.add(new PatternTopic(systemKey + key));
			} else {
				topics.add(new ChannelTopic(systemKey + key));
			}
		}

		container.addMessageListener(new RedisHandler<>(handler), topics);
	}

	private static final class RedisHandler<M> implements MessageListener {

		private IMsgHandler<M> handler;

		private Class<M> clz;

		@SuppressWarnings("unchecked")
		public RedisHandler(IMsgHandler<M> handler) {
			this.handler = handler;
			String clzName = handler.getClass().getGenericSuperclass().getTypeName();
			try {
				this.clz = (Class<M>) Class.forName(clzName);
			} catch (ClassNotFoundException e) {
				log.error(e);
			}
			this.handler = handler;
		}

		@Override
		public void onMessage(Message message, byte[] pattern) {
			log.info(pattern);
			log.info(message);
			Object body = JSON.parse(message.getBody());
			M msg = TypeUtils.cast(body, clz, ParserConfig.global);
			handler.onMessage(msg);
		}
	}
}
