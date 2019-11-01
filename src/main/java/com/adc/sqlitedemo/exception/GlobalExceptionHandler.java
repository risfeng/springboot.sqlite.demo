package com.adc.sqlitedemo.exception;

import com.adc.sqlitedemo.common.response.HttpResult;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局处理器
 *
 * @author risfeng
 * @date 2019/11/02
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 默认异常处理器
   *
   * @param request 请求体
   * @param e Exception
   * @return HttpResult
   */
  @ExceptionHandler({Exception.class})
  public HttpResult<Object> onException(HttpServletRequest request, Exception e) {
    log.error(e.getMessage(), e);
    return HttpResult.error("服务器异常，请联系管理员：" + e.getMessage());
  }

  /**
   * 通用异常处理器
   *
   * @param request 请求体
   * @param e GeneralException
   * @return HttpResult
   */
  @ExceptionHandler({GeneralException.class})
  public HttpResult<Object> onGeneralException(HttpServletRequest request, GeneralException e) {
    log.error(e.getMessage(), e);
    return HttpResult.error(e.getMessage());
  }

  /**
   * validation 异常处理
   *
   * @param request 请求体
   * @param e ConstraintViolationException
   * @return HttpResult
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public HttpResult<Object> onConstraintViolationException(HttpServletRequest request,
      ConstraintViolationException e) {
    Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
    if (!CollectionUtils.isEmpty(constraintViolations)) {
      String errorMessage = constraintViolations
          .stream()
          .map(ConstraintViolation::getMessage)
          .collect(Collectors.joining(";"));
      return HttpResult.error(errorMessage);
    }
    return HttpResult.error(e.getMessage());
  }

  /**
   * validation 异常处理
   *
   * @param request 请求体
   * @param e MethodArgumentNotValidException
   * @return HttpResult
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public HttpResult<Object> onMethodArgumentNotValidException(HttpServletRequest request,
      MethodArgumentNotValidException e) {
    List<FieldError> objectErrors = e.getBindingResult().getFieldErrors();
    if (!CollectionUtils.isEmpty(objectErrors)) {
      String errorMessage = objectErrors
          .stream()
          .map(error -> String.format("[%s]%s", error.getField(), error.getDefaultMessage()))
          .collect(Collectors.joining(";"));
      return HttpResult.error(errorMessage);
    }
    List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
    if (!CollectionUtils.isEmpty(allErrors)) {
      String firstErrorMessage = allErrors.get(0).getDefaultMessage();
      return HttpResult.error(firstErrorMessage);
    }
    return HttpResult.error(e.getMessage());
  }
}


