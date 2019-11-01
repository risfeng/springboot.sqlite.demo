package com.adc.sqlitedemo.common.response;

import com.adc.sqlitedemo.common.enums.HttpResultStatusEnum;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 统一请求返回对象
 *
 * @author risfeng
 * @date 2019/11/02
 */
@Data
@Slf4j
@ApiModel(value = "请求返回")
public class HttpResult<T> implements Serializable {

  private static final long serialVersionUID = -5457994478596909451L;
  /**
   * 状态码：0成功，其他失败
   */
  @ApiModelProperty(value = "状态码：0成功，其他失败")
  private int code;

  /**
   * 消息
   */
  @ApiModelProperty(value = "消息")
  private String message;

  /**
   * 返回数据
   */
  @ApiModelProperty(value = "返回数据")
  private T data;

  public interface ResultExe<T> {

    /**
     * Exception
     *
     * @return T
     * @throws Exception Exception
     */
    public T exe() throws Exception;
  }

  public static <T> HttpResult<T> returnResult(ResultExe<T> exe, org.slf4j.Logger logger) {
    try {
      T t = exe.exe();
      if (t == null) {
        log.warn("result success but data is null.");
        return create(HttpResultStatusEnum.SUCCESS.getCode(), "Data is null ！", null);
      } else {
        log.info(HttpResultStatusEnum.SUCCESS.name());
        return create(HttpResultStatusEnum.SUCCESS.getCode(), "SUCCESS", t);
      }

    } catch (Throwable e) {
      log.error("[{}]操作结果异常.", HttpResultStatusEnum.FAILED.name(), e);
      return create(HttpResultStatusEnum.FAILED.getCode(), "操作结果异常：" + e.getMessage(), null);

    }
  }

  public static <T> HttpResult<T> create(int code, String message) {
    return new HttpResult<T>(code, message, null);
  }

  public static <T> HttpResult<T> create(int code, String message, T data) {
    return new HttpResult<T>(code, message, data);
  }

  public static <T> HttpResult<T> success(String message, T data) {
    return create(HttpResultStatusEnum.SUCCESS.getCode(), message, data);
  }

  public static <T> HttpResult<T> success(String message) {
    return success(message, null);
  }

  public static <T> HttpResult<T> successData(T data) {
    return success(HttpResultStatusEnum.SUCCESS.name(), data);
  }

  public static <T> HttpResult<T> success() {
    return success(HttpResultStatusEnum.SUCCESS.name());
  }

  public static <T> HttpResult<T> error(String message, T data) {
    return create(HttpResultStatusEnum.FAILED.getCode(), message, data);
  }

  public static <T> HttpResult<T> error() {
    return error(HttpResultStatusEnum.FAILED.name());
  }

  public static <T> HttpResult<T> error(T data) {
    return error(HttpResultStatusEnum.FAILED.name(), data);
  }

  public static <T> HttpResult<T> error(String message) {
    return error(message, null);
  }

  public HttpResult() {
    message = "";
  }

  public HttpResult(int status, String info, T result) {
    code = status;
    message = info;
    data = result;
  }

  /**
   * 转JSON字符串
   *
   * @return Json字符串
   */
  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}

