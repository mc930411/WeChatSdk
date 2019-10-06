package com.sce.sdk.wechatlibrary.data;

public class MessageInfo {

  public static final String TAG = "MessageInfo";


  /**   参数： 消息类型、用户类型、消息内容        **/
  int messageType;
  int userType;
  Object data;
  String userId;

  public MessageInfo(int type, int user, Object data, String userId) {
    this.messageType = type;
    this.data = data;
    this.userType = user;
    this.userId = userId;
  }

  public int getMessageType() {
    return this.messageType;
  }

  public int getUserType() {
    return this.userType;
  }

  public Object getData() {
    return this.data;
  }

  public String getUserId() { return this.userId; }

  public void setData(Object obj) {
    this.data = obj;
  }

  public void setUserId(String userId) { this.userId = userId; }

  public void setMessageType(int type) { this.messageType = type; }

}
