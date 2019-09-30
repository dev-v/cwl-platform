package com.cwl.platform.web.resolver;

import com.cwl.platform.entity.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chenwl 2019/9/30
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {
  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    if (body instanceof Response) {
      return body;
    } else {
      return Response.newSuccessResponse(body);
    }
  }
}
