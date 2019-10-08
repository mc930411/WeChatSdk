package com.sce.sdk.wechatlibrary.ui;

import static com.sce.sdk.wechatlibrary.config.MessageConfig.MESSAGE_TEXT;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sce.sdk.wechatlibrary.R;
import com.sce.sdk.wechatlibrary.WeChatManager;
import com.sce.sdk.wechatlibrary.data.MessageInfo;
import com.sce.sdk.wechatlibrary.ui.ChatAdapter.OnRecyclerViewItemLongClick;
import com.sce.sdk.wechatlibrary.ui.OptionAdapter.OnRecyclerViewItemClick;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuant
 */
public class WeChatActivity extends Activity {

  private static final String TAG = "WeChatAvtivity";

  private RecyclerView mRecyclerView;
  private LinearLayoutManager mLinearLayoutManager;
  private WeChatManager manager;

  /** for wechat **/
  private List<MessageInfo> weChatList;
  private float mRawX;
  private float mRawY;
  private PopupWindow mPopWindow;
  private View mPopMenu;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wechat);
    //initRecyclerView();
    initData();
    initWeChatView();
  }

  private void initData() {
    manager = WeChatManager.get();
    weChatList = WeChatManager.get().mChatMessageQueue;
  }

  private void initWeChatView() {
    mRecyclerView = findViewById(R.id.recyclerview_messages);
    mLinearLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    manager.mChatAdapter = new ChatAdapter(getApplicationContext());
    manager.mChatAdapter.setChatMessages(weChatList);

    manager.mChatAdapter.setmOnRecyclerViewItemLongClick(new OnRecyclerViewItemLongClick() {
      @Override
      public void onItemLongClick(View childView, MotionEvent event, int position) {
        mRawX = event.getRawX();
        mRawY = event.getRawY();
        Log.d(TAG, "e.getRawX()横坐标=" + mRawX + ", e.getRawY()纵坐标=" + mRawY);
        Log.d(TAG, "position=" + position);
        initPopMenu(childView, position);
      }
    });

    mRecyclerView.setAdapter(manager.mChatAdapter);
    mRecyclerView.scrollToPosition(manager.mChatAdapter.getItemCount() - 1);

  }

  private void initPopMenu(final View selectedView, final int chatPosition) {
    final List<String> optionEntities = new ArrayList<>();
    optionEntities.add(getResources().getString(R.string.option_copy));
    optionEntities.add(getResources().getString(R.string.option_share));
    optionEntities.add(getResources().getString(R.string.option_collect));
    optionEntities.add(getResources().getString(R.string.option_remind));
    optionEntities.add(getResources().getString(R.string.option_translate));
    optionEntities.add(getResources().getString(R.string.option_delete));
    optionEntities.add(getResources().getString(R.string.option_formore));

    if(mPopMenu == null) {
      mPopMenu = View.inflate(this, R.layout.menu_option, null);
    }
    RecyclerView rvOptions = mPopMenu.findViewById(R.id.recyclerview_options);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    rvOptions.setLayoutManager(linearLayoutManager);
    OptionAdapter optionAdapter = new OptionAdapter(getApplicationContext());
    optionAdapter.setOptionEntities(optionEntities);
    optionAdapter.setmOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
      @Override
      public void onItemClick(View childView, int position) {
        MessageInfo messageInfo = manager.mChatAdapter.chatMessages.get(chatPosition);
        if(messageInfo.getMessageType() == MESSAGE_TEXT) {
          messageInfo.setData(optionEntities.get(position));
        }
        manager.mChatAdapter.notifyItemChanged(chatPosition);
      }
    });
    rvOptions.setAdapter(optionAdapter);
    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    mPopMenu.measure(w, h);
    int viewWidth = mPopMenu.getMeasuredWidth();
    int viewHeight = mPopMenu.getMeasuredHeight();
    if(mPopWindow == null) {
      mPopWindow = new PopupWindow(mPopMenu, viewWidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
    }
    mPopWindow.setOutsideTouchable(true);
    mPopWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.NO_GRAVITY, (int) mRawX, (int) mRawY);
    mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {
        selectedView.setSelected(false);
      }
    });
  }

}
