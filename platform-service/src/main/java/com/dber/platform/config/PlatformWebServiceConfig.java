package com.dber.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletCookieValueMethodArgumentResolver;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class PlatformWebServiceConfig extends WebMvcConfigurationSupport {

  @Override
  protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    addDefaultHttpMessageConverters(converters);
    for (HttpMessageConverter converter : converters) {
      if (converter instanceof StringHttpMessageConverter) {
        converters.remove(converter);
        break;
      }
    }
    converters.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
  }

  @Autowired
  private ConfigurableBeanFactory beanFactory;


  public static MethodParameter interfaceMethodParameter(MethodParameter parameter, Class annotationType) {
    if (!parameter.hasParameterAnnotation(annotationType)) {
      for (Class<?> cls : parameter.getDeclaringClass().getInterfaces()) {
        try {
          MethodParameter methodParameter = new MethodParameter(
                  cls.getMethod(parameter.getMethod().getName(), parameter.getMethod().getParameterTypes()),
                  parameter.getParameterIndex()
          );
          if (methodParameter.hasParameterAnnotation(annotationType)) {
            return methodParameter;
          }
        } catch (NoSuchMethodException e) {
          continue;
        }
      }
    }
    return parameter;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {
    list.add(new PathVariableMethodArgumentResolver() {  // PathVariable 支持接口注解
      @Override
      public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(interfaceMethodParameter(parameter, PathVariable.class));
      }

      @Override
      protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        return super.createNamedValueInfo(interfaceMethodParameter(parameter, PathVariable.class));
      }
    });

    list.add(new RequestResponseBodyMethodProcessor(getMessageConverters()) {    // RequestBody 支持接口注解
      @Override
      public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(interfaceMethodParameter(parameter, RequestBody.class));
      }

      @Override
      protected void validateIfApplicable(WebDataBinder binder, MethodParameter methodParam) {    // 支持@Valid验证
        super.validateIfApplicable(binder, interfaceMethodParameter(methodParam, Valid.class));
      }
    });

    list.add(new RequestHeaderMethodArgumentResolver(beanFactory) {  // RequestHeader 支持接口注解
      @Override
      public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(interfaceMethodParameter(parameter, RequestHeader.class));
      }

      @Override
      protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        return super.createNamedValueInfo(interfaceMethodParameter(parameter, RequestHeader.class));
      }
    });

    list.add(new ServletCookieValueMethodArgumentResolver(beanFactory) {  // CookieValue 支持接口注解
      @Override
      public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(interfaceMethodParameter(parameter, CookieValue.class));
      }

      @Override
      protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        return super.createNamedValueInfo(interfaceMethodParameter(parameter, CookieValue.class));
      }
    });
  }

}
