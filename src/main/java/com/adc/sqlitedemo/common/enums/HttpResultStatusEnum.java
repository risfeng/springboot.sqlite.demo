package com.adc.sqlitedemo.common.enums;

/**
 * 返回状态枚举
 *
 * @author risfeng
 */
public enum HttpResultStatusEnum {
  /**
   * SUCCESS 0
   */
  SUCCESS(0),

  /**
   * Failed -1
   */
  FAILED(-1);

  private int code;

  HttpResultStatusEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
