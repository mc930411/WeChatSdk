package com.sce.sdk.wechatlibrary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.sce.sdk.wechatlibrary.data.MessageInfo;
import com.sce.sdk.wechatlibrary.ui.ChatAdapter;
import com.sce.sdk.wechatlibrary.ui.WeChatActivity;
import java.util.List;

public class WeChatManager {

  public static final String VERSION = "1.0.0";
  private static final String TAG = "WeChatManager";
  private static WeChatManager weChatInstance;

  private Context mContext;
  public List<MessageInfo> mChatMessageQueue;
  public ChatAdapter mChatAdapter;


  private WeChatManager(Context context) {
    this.mContext = context;
  }

  public static WeChatManager get() {
    return weChatInstance;
  }

  public static WeChatManager with(Context context) {
    if (weChatInstance == null) {
      synchronized (WeChatManager.class) {
        if(weChatInstance == null) {
          setWeChatSingleton(new Builder(context).build());
        }
      }
    }
    return weChatInstance;
  }

  public static void setWeChatSingleton(WeChatManager weChatManager) {
    weChatInstance = weChatManager;
  }

  public static int setMessageQueue(List<MessageInfo> queue) {
    weChatInstance.mChatMessageQueue = queue;
    if(queue == null) {
      Log.e(TAG, "MessageQueue must not be NULL");
      return -1;
    }
    return 0;
  }

  public static void start() {
    weChatInstance.startApply();
  }

  private void startApply() {
    Intent intent = new Intent(this.mContext, WeChatActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
    mContext.startActivity(intent);
  }

  public static int notifyItem(int position) {
    if(weChatInstance.mChatMessageQueue == null) {
      Log.e(TAG, "There is no MessageQueue, Please use method : setMessageQueue()");
      return -1;
    }
    if(weChatInstance.mChatMessageQueue.size() - 1 < position) {
      Log.e(TAG, "position is outOfRange, Please check : WeChatManager.get().MessageQueue.size()");
      return -1;
    }
    weChatInstance.mChatAdapter.notifyItemChanged(position);
    return 0;
  }

  public static int notifyAllItem() {
    if(weChatInstance.mChatMessageQueue == null) {
      Log.e(TAG, "There is no MessageQueue, Please use method : setMessageQueue()");
      return -1;
    }
    weChatInstance.mChatAdapter.notifyDataSetChanged();
    return 0;
  }

  public static class Builder {

    private Context context;

    public Builder(Context context) {
      if (context == null) {
        throw new IllegalArgumentException("Context must not be null.");
      } else {
        this.context = context;
      }
    }

    public WeChatManager build() {
      Context appContext = this.context.getApplicationContext();
      return new WeChatManager(appContext);
    }
  }


}
