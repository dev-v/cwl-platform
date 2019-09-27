package com.cwl.tool.util;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wenlongchen
 * @since Nov 14, 2016
 */
public class SpringUtil {
  private static ApplicationContext applicationContext;

  private static AtomicInteger atomicInteger = new AtomicInteger(1);

  public static final void setApplicationContext(ApplicationContext applicationContext) {
    SpringUtil.applicationContext = applicationContext;
  }

  public static <T> void registBean(String beanName, Class<T> clazz, Object[] constructorArgs) {
    registBean(beanName, clazz, constructorArgs, null);
  }

  public static <T> void registBean(String beanName, Class<T> clazz, Map<String, Object> properties) {
    registBean(beanName, clazz, null, properties);
  }

  public static <T> void registBean(String beanName, Class<T> clazz) {
    registBean(beanName, clazz, null, null);
  }

  public static <T> String registBean(Class<T> clazz, Object[] constructorArgs,
                                      Map<String, Object> properties) {
    String beanName = clazz.getSimpleName() + "-spring-util-" + atomicInteger.incrementAndGet();
    registBean(beanName, clazz, constructorArgs, properties);
    return beanName;
  }

  public static <T> String registBean(Class<T> clazz, Object[] constructorArgs) {
    return registBean(clazz, constructorArgs, null);
  }

  public static <T> String registBean(Class<T> clazz, Map<String, Object> properties) {
    return registBean(clazz, null, properties);
  }

  public static <T> String registBean(Class<T> clazz) {
    return registBean(clazz, null, null);
  }

  public static <T> void registBean(String beanName, Class<T> clazz, Object[] constructorArgs,
                                    Map<String, Object> properties) {
    DefaultListableBeanFactory beanFactory =
            (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

    BeanDefinitionBuilder beanDefinitionBuilder =
            BeanDefinitionBuilder.genericBeanDefinition(clazz);

    if (constructorArgs != null) {
      for (Object arg : constructorArgs) {
        beanDefinitionBuilder.addConstructorArgValue(arg);
      }
    }
    if (properties != null) {
      for (Entry<String, Object> entry : properties.entrySet()) {
        beanDefinitionBuilder.addPropertyValue(entry.getKey(), entry.getValue());
      }
    }

    beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
  }


  public static <T> T getBean(String name, Class<T> requiredType) {
    return applicationContext.getBean(name, requiredType);
  }

  public static <T> T getBean(Class<T> requiredType) {
    return applicationContext.getBean(requiredType);
  }

  public static Object getBean(String name) {
    return applicationContext.getBean(name);
  }
}

