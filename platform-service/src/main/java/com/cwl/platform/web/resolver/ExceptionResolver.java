package com.cwl.platform.web.resolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwl.platform.entity.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * <li>文件名称: ExceptionResolver.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月21日
 */
@RestControllerAdvice
public class ExceptionResolver implements HandlerExceptionResolver {

  private Log log = LogFactory.getLog(ExceptionResolver.class);

  @Autowired
  private View jsonView;

  /**
   * 这里可以统一做异常处理 登录跳转等操作
   */
  @Override
  @ExceptionHandler(Throwable.class)
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                       Exception ex) {
    String msg = ex.getMessage();
    Map<String, Object> errorResponse = Response.newFailureResponse(msg).getErrorMsg();

    log.error(errorResponse);
    log.error(ExceptionUtils.getStackTrace(ex));

    ModelAndView mv = new ModelAndView();
    mv.setView(jsonView);

    mv.addAllObjects(errorResponse);

    return mv;
  }
}
