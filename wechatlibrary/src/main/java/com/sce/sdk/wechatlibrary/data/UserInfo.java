package com.sce.sdk.wechatlibrary.data;

import android.net.Uri;

public class UserInfo {
  public int userType;
  public Uri userIcon;

  public UserInfo(int userType) {
    this.userType = userType;
  }

}
