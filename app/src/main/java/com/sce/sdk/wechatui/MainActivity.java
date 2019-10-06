package com.sce.sdk.wechatui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.sce.sdk.wechatlibrary.WeChatManager;
import com.sce.sdk.wechatlibrary.config.MessageConfig;
import com.sce.sdk.wechatlibrary.data.MessageInfo;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String TAG = "MainActivity";
  private static final int READ_REQUEST_CODE = 10;
  private List<MessageInfo> list;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button btn = findViewById(R.id.btnTest);
    btn.setOnClickListener(this);
    Button btnAdd = findViewById(R.id.addImage);
    btnAdd.setOnClickListener(this);
    init();
  }

  @Override
  public void onClick(View view) {
    if(view.getId() == R.id.btnTest) {
//      Intent intent = new Intent(this, WeChatActivity.class);
//      startActivity(intent);
      WeChatManager.start();
    } else if(view.getId() == R.id.addImage) {
      performFileSearch();
    }
  }

  private void init() {
    WeChatManager manager = WeChatManager.with(getApplicationContext());
    list = new ArrayList<>();
    list.add(new MessageInfo(MessageConfig.MESSAGE_TEXT, MessageConfig.USER_SENT, "我发送了消息1", "Admin"));
    list.add(new MessageInfo(MessageConfig.MESSAGE_TEXT, MessageConfig.USER_RECV, "我发送了消息2", "Amy"));
    list.add(new MessageInfo(MessageConfig.MESSAGE_TEXT, MessageConfig.USER_SENT, "我发送了消息3", "Admin"));
    list.add(new MessageInfo(MessageConfig.MESSAGE_TEXT, MessageConfig.USER_SENT, "我发送了消息4", "Admin"));
    list.add(new MessageInfo(MessageConfig.MESSAGE_SYSTEMINFO, MessageConfig.USER_SENT, "我发送了消息5", "Admin"));
    list.add(new MessageInfo(MessageConfig.MESSAGE_TEXT, MessageConfig.USER_RECV, "我发送了消息6", "Mary"));
    list.add(new MessageInfo(MessageConfig.MESSAGE_SYSTEMINFO, MessageConfig.USER_RECV, "我发送了消息7", "Jeana"));
    WeChatManager.setMessageQueue(list);
  }

  /**
   * Fires an intent to spin up the "file chooser" UI and select an image.
   * 视图检索文件
   */
  public void performFileSearch() {

    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("image/*");
    startActivityForResult(intent, READ_REQUEST_CODE);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
      Intent resultData) {
    super.onActivityResult(requestCode, resultCode, resultData);
    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      Uri uri = null;
      if (resultData != null) {
        uri = resultData.getData();
        Log.i(TAG, "Uri: " + uri.toString());
        list.add(new MessageInfo(MessageConfig.MESSAGE_IMAGE, MessageConfig.USER_SENT, uri, "Admin"));
      }
    }
  }
}
