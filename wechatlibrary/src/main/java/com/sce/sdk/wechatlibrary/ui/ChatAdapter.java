package com.sce.sdk.wechatlibrary.ui;


import static com.sce.sdk.wechatlibrary.config.MessageConfig.MESSAGE_IMAGE;
import static com.sce.sdk.wechatlibrary.config.MessageConfig.MESSAGE_SYSTEMINFO;
import static com.sce.sdk.wechatlibrary.config.MessageConfig.MESSAGE_TEXT;
import static com.sce.sdk.wechatlibrary.config.MessageConfig.USER_RECV;
import static com.sce.sdk.wechatlibrary.config.MessageConfig.USER_SENT;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sce.sdk.wechatlibrary.R;
import com.sce.sdk.wechatlibrary.config.MessageConfig;
import com.sce.sdk.wechatlibrary.data.MessageInfo;
import com.sce.sdk.wechatlibrary.utils.UtilsTool;
import java.util.List;

/**
 * @author yuant
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final String TAG  = "ChatAdaptor";

  private Context mContext;
  public List<MessageInfo> chatMessages;
  private OnRecyclerViewItemLongClick mOnRecyclerViewItemLongClick;

  public ChatAdapter(Context context) {
    this.mContext = context;
  }

  public void setChatMessages(List<MessageInfo> chatMessages) {
    this.chatMessages = chatMessages;
  }

  public void setmOnRecyclerViewItemLongClick(OnRecyclerViewItemLongClick onRecyclerViewItemLongClick) {
    this.mOnRecyclerViewItemLongClick = onRecyclerViewItemLongClick;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    switch (viewType) {
      case USER_SENT + MESSAGE_TEXT:
        return new HolderChatSend(
            LayoutInflater.from(mContext).inflate(R.layout.rv_item_chat_msg_send, parent, false), MESSAGE_TEXT);
      case USER_SENT + MESSAGE_IMAGE:
        return new HolderChatSend(
            LayoutInflater.from(mContext).inflate(R.layout.rv_item_chat_image_send, parent, false), MESSAGE_IMAGE);
      case USER_SENT + MESSAGE_SYSTEMINFO:
        return new HolderChatSend(
            LayoutInflater.from(mContext).inflate(R.layout.rv_item_chat_info, parent, false), MESSAGE_SYSTEMINFO);
      case USER_RECV + MESSAGE_TEXT:
        return new HolderChatReceive(
            LayoutInflater.from(mContext).inflate(R.layout.rv_item_chat_msg_receive, parent, false), MESSAGE_TEXT);
      case USER_RECV + MESSAGE_IMAGE:
        return new HolderChatReceive(
            LayoutInflater.from(mContext).inflate(R.layout.rv_item_chat_image_receive, parent, false), MESSAGE_IMAGE);
      case USER_RECV + MESSAGE_SYSTEMINFO:
        return new HolderChatReceive(
            LayoutInflater.from(mContext).inflate(R.layout.rv_item_chat_info, parent, false), MESSAGE_SYSTEMINFO);
      default:
        return null;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (chatMessages != null && chatMessages.size() > 0) {
      int from = chatMessages.get(position).getUserType();
      int type = chatMessages.get(position).getMessageType();
      return from + type;
    }
    return 0;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (chatMessages != null) {
      MessageInfo chatMessage = chatMessages.get(position);
      int type = chatMessage.getMessageType();
      int user = chatMessage.getUserType();
      if(user == USER_SENT) {
        setSentData((HolderChatSend)holder, type, chatMessage.getData());
      } else if(user == USER_RECV) {
        setRecvData((HolderChatReceive)holder, type, chatMessage.getData(), chatMessage.getUserId());
      }
    }
  }

  private void setSentData(HolderChatSend holderChatSend, int type, Object data) {
    String dataType = UtilsTool.getType(data);
    switch (type) {
      case MESSAGE_TEXT:
        String msg = mContext.getResources().getString(R.string.data_type_error);
        if(dataType.contains(MessageConfig.TYPR_STRING)) {
          msg = data.toString();
        } else {
          Log.e(TAG, "Text MessageType error");
        }
        holderChatSend.tvMsgContent.setText(msg);
        break;
      case MESSAGE_IMAGE:
        if(dataType.contains(MessageConfig.TYPE_URI)) {
          Uri uri = (Uri)data;
          holderChatSend.tvMsgImage.setImageURI(uri);
        } else {
          Log.e(TAG, "Image MessageType error");
        }
        break;
      case MESSAGE_SYSTEMINFO:
        String sysInfo = mContext.getResources().getString(R.string.data_type_error);
        if(dataType.contains(MessageConfig.TYPR_STRING)) {
          sysInfo = data.toString();
        } else {
          Log.e(TAG, "SystemInfo MessageType error");
        }
        holderChatSend.tvSystemInfo.setText(sysInfo);
        break;
      default:
        break;
    }
  }

  private void setRecvData(HolderChatReceive holderChatReceive, int type, Object data, String userId) {
    if(type != MESSAGE_SYSTEMINFO && userId != null) {
      holderChatReceive.tvNickName.setText(userId);
    }
    String dataType = UtilsTool.getType(data);
    switch (type) {
      case MESSAGE_TEXT:
        String msg = mContext.getResources().getString(R.string.data_type_error);
        if(dataType.contains(MessageConfig.TYPR_STRING)) {
          msg = data.toString();
        } else {
          Log.e(TAG, "Text MessageType error");
        }
        holderChatReceive.tvMsgContent.setText(msg);
        break;
      case MESSAGE_IMAGE:
        if(dataType.contains(MessageConfig.TYPE_URI)) {
          Uri uri = (Uri)data;
          holderChatReceive.tvMsgImage.setImageURI(uri);
        } else {
          Log.e(TAG, "Image MessageType error");
        }
        break;
      case MESSAGE_SYSTEMINFO:
        String info = mContext.getResources().getString(R.string.data_type_error);
        if(dataType.contains(MessageConfig.TYPR_STRING)) {
          info = data.toString();
        } else {
          Log.e(TAG, "SystemInfo MessageType error");
        }
        holderChatReceive.tvSystemInfo.setText(info);
        break;
      default:
        break;
    }

  }

  @Override
  public int getItemCount() {
    if (chatMessages != null && chatMessages.size() > 0) {
      return chatMessages.size();
    }
    return 0;
  }

  public class HolderChatSend extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    TextView tvNickName;
    TextView tvMsgContent;
    ImageView tvMsgImage;
    TextView tvSystemInfo;
    LinearLayout layoutChat;
    MotionEvent event;

    public HolderChatSend(View itemView, int type) {
      super(itemView);
      tvNickName = itemView.findViewById(R.id.textview_nick_name);
      switch(type) {
        case MESSAGE_TEXT:
          tvMsgContent = (TextView) itemView.findViewById(R.id.textview_send_message);
          break;
        case MESSAGE_IMAGE:
          tvMsgImage = itemView.findViewById(R.id.imageview_send_message);
          break;
        case MESSAGE_SYSTEMINFO:
          tvSystemInfo = itemView.findViewById(R.id.textview_systeminfo);
          break;
        default:
          break;
      }
      if(type != MESSAGE_SYSTEMINFO) {
        layoutChat = itemView.findViewById(R.id.layout_message);
        layoutChat.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent e) {
            switch (e.getAction()) {
              case MotionEvent.ACTION_DOWN:
                event = e;
                break;
              default:
                break;
            }
            // 如果onTouch返回false,首先是onTouch事件的down事件发生，此时，如果长按，触发onLongClick事件；
            // 然后是onTouch事件的up事件发生，up完毕，最后触发onClick事件。
            return false;
          }
        });
        layoutChat.setOnLongClickListener(this);
      }

    }

    @Override
    public boolean onLongClick(View v) {
      if (mOnRecyclerViewItemLongClick != null) {
        mOnRecyclerViewItemLongClick.onItemLongClick(v, event, getAdapterPosition());
      }
      return false;
    }
  }

  public class HolderChatReceive extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    TextView tvNickName;
    TextView tvMsgContent;
    ImageView tvMsgImage;
    TextView tvSystemInfo;
    MotionEvent event;
    LinearLayout layoutChat;

    public HolderChatReceive(View itemView, int type) {
      super(itemView);
      tvNickName = itemView.findViewById(R.id.textview_nick_name);
      switch(type) {
        case MESSAGE_TEXT:
          tvMsgContent = itemView.findViewById(R.id.textview_receive_message);
          break;
        case MESSAGE_IMAGE:
          tvMsgImage = itemView.findViewById(R.id.imageview_receive_message);
          break;
        case MESSAGE_SYSTEMINFO:
          tvSystemInfo = itemView.findViewById(R.id.textview_systeminfo);
          break;
        default:
          break;
      }

      if(type != MESSAGE_SYSTEMINFO) {
        layoutChat = itemView.findViewById(R.id.layout_message);
        layoutChat.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent e) {
            switch (e.getAction()) {
              case MotionEvent.ACTION_DOWN:
                event = e;
                break;
              default:
                break;
            }
            // 如果onTouch返回false,首先是onTouch事件的down事件发生，此时，如果长按，触发onLongClick事件；
            // 然后是onTouch事件的up事件发生，up完毕，最后触发onClick事件。
            return false;
          }
        });
        layoutChat.setOnLongClickListener(this);
      }
    }

    @Override
    public boolean onLongClick(View v) {
      if (mOnRecyclerViewItemLongClick != null) {
        mOnRecyclerViewItemLongClick.onItemLongClick(v, event, getAdapterPosition());
      }
      return false;
    }
  }

  /**
   * item点击
   */
  public interface OnRecyclerViewItemLongClick {
    void onItemLongClick(View childView, MotionEvent event, int position);
  }

}
