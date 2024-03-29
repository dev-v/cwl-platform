package com.cwl.platform.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.cwl.platform.web.resolver.FastJsonArgumentResolver;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
import org.springframework.web.servlet.view.AbstractView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.cwl.platform.web.resolver")
public class PlatformWebServiceConfig extends WebMvcConfigurationSupport {
  static {
    JSON.DEFAULT_PARSER_FEATURE |= SerializerFeature.WriteDateUseDateFormat.getMask();
    JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  }

  @Override
  protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//    addDefaultHttpMessageConverters(converters);
//    for (HttpMessageConverter converter : converters) {
//      if (converter instanceof StringHttpMessageConverter) {
//        converters.remove(converter);
//        break;
//      }
//    }
//    converters.add(new StringHttpMessageConverter(Charset.forName("utf-8")));

    converters.add(fastJsonHttpMessageConverter());
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://localhost:8000",
                    "http://localhost:8001",
                    "http://localhost:8002",
                    "http://127.0.0.1:8000",
                    "http://localhost:3000",
                    "http://127.0.0.1:3000",
                    "http://127.0.0.1:8001",
                    "http://127.0.0.1:8002")
            .allowCredentials(true)
            .allowedHeaders("*")
            .exposedHeaders(HttpHeaders.SET_COOKIE)
            .maxAge(1800);
  }

  @Bean
  public CharacterEncodingFilter characterEncodingFilter() {
    CharacterEncodingFilter filter = new CharacterEncodingFilter();
    filter.setEncoding("utf-8");
    filter.setForceEncoding(true);
    return filter;
  }

  @Bean
  public AbstractView jsonView() {
    return new FastJsonJsonView();
  }

  @Bean
  public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

    FastJsonConfig config = converter.getFastJsonConfig();
    config.setSerializerFeatures(SerializerFeature.BrowserSecure,
            SerializerFeature.WriteDateUseDateFormat, SerializerFeature.PrettyFormat);
//        config.setDateFormat(JSON.DEFFAULT_DATE_FORMAT);
    config.setParserConfig(ParserConfig.global);

    List<MediaType> supportedMediaTypes = new ArrayList<>();
    supportedMediaTypes.add(MediaType.APPLICATION_JSON);
    supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
    supportedMediaTypes.add(MediaType.TEXT_PLAIN);
//        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
//        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
//        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
//        supportedMediaTypes.add(MediaType.APPLICATION_XML);
//        supportedMediaTypes.add(MediaType.IMAGE_GIF);
//        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
//        supportedMediaTypes.add(MediaType.IMAGE_PNG);
//        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
//        supportedMediaTypes.add(MediaType.TEXT_HTML);
//        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
//        supportedMediaTypes.add(MediaType.TEXT_XML);
    converter.setSupportedMediaTypes(supportedMediaTypes);
    return converter;
  }

  @Bean
  public RequestParamMapMethodArgumentResolver requestParamMapMethodArgumentResolver() {
    return new RequestParamMapMethodArgumentResolver();
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

    list.add(new FastJsonArgumentResolver());
  }

}
