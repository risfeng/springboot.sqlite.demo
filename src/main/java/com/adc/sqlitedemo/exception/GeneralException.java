package com.adc.sqlitedemo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常基类
 *
 * @author risfeng
 * @date 2019/11/02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralException extends RuntimeException {

  private static final long serialVersionUID = -7499041257846305832L;
  /**
   * 0成功 其他失败
   */
  private int code = 1;
  /**
   * 消息
   */
  private String msg;

  /**
   * 异常编码
   */

  public GeneralException(String msg) {
    super(msg);
    this.msg = msg;
  }

  public GeneralException(String msg, Throwable e) {
    super(msg, e);
    this.msg = msg;
  }

  public GeneralException(String msg, int code) {
    super(msg);
    this.msg = msg;
    this.code = code;
  }

  public GeneralException(String msg, int code, Throwable e) {
    super(msg, e);
    this.msg = msg;
    this.code = code;
  }
}
