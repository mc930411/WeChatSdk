package com.sce.sdk.wechatlibrary.utils;

/**
 * @author yuant
 */
public class UtilsTool {

  /**
   * 获取数据类型
   * @param object
   * @return
   */
  public static String getType(Object object){
    String typeName=object.getClass().getName();
    int length= typeName.lastIndexOf(".");
    String type =typeName.substring(length+1);
    return type;
  }

}
