package com.sce.sdk.wechatlibrary.config;

import com.sce.sdk.wechatlibrary.R;

/**
 * @author yuant
 */
public class MessageConfig {

  public static final int MESSAGE_TEXT = 0;
  public static final int MESSAGE_IMAGE = 1;
  public static final int MESSAGE_SYSTEMINFO = 2;
  public static final int MESSAGE_VIDEO = 3;

  /** 支持0~9种数据格式 **/
  public static final int USER_SENT = 0;
  public static final int USER_RECV = 10;

  /** 数据类型校验 **/
  public static final String TYPR_STRING = "String";
  public static final String TYPE_URI = "Uri";

  /**  异常提示 **/
  public static final String DATA_TYPE_ERROR = "[消息类型设置错误]";
  public static final int IMAGE_TYPE_ERROR = R.drawable.disable;

}
