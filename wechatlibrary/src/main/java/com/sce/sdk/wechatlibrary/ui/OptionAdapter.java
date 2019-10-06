package com.sce.sdk.wechatlibrary.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.sce.sdk.wechatlibrary.R;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  List<String> optionEntities;
  Context mContext;
  private OnRecyclerViewItemClick mOnRecyclerViewItemClick;

  public OptionAdapter(Context context) {
    this.mContext = context;
  }

  public void setOptionEntities(List<String> options) {
    this.optionEntities = options;
  }

  public void setmOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
    this.mOnRecyclerViewItemClick = onRecyclerViewItemClick;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new HolderOption(
        LayoutInflater.from(mContext).inflate(R.layout.item_option, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String option = optionEntities.get(position);
    if (optionEntities != null && optionEntities.size() > 0) {
      ((HolderOption)holder).option.setText(option);
    }
  }

  @Override
  public int getItemCount() {
    if (optionEntities != null && optionEntities.size() > 0) {
      return optionEntities.size();
    }
    return 0;
  }


  public class HolderOption extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView option;

    public HolderOption(View itemView) {
      super(itemView);
      option = itemView.findViewById(R.id.textview_option_name);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mOnRecyclerViewItemClick != null) {
        mOnRecyclerViewItemClick.onItemClick(view, getAdapterPosition());
      }
    }
  }

  /**
   * 弹窗item点击
   */
  public interface OnRecyclerViewItemClick {
    void onItemClick(View childView, int position);
  }
}
