package com.chill.learn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.R;
import com.chill.learn.entity.ChatItem;

import java.util.List;

public class ChatListAdapter extends BaseAdapter<ChatItem> {
  private List<ChatItem> mChatList;

  public ChatListAdapter(List<ChatItem> data) {
    super(data);
    mChatList = data;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
    return new ChatListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
    ChatListViewHolder holder = (ChatListViewHolder) Holder;
    ChatItem chatItem = mChatList.get(position);
    holder.bindData(chatItem);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.item_chat_list;
  }

  public static class ChatListViewHolder extends RecyclerView.ViewHolder {
    private ImageView mAvatar;
    private TextView mName;
    private TextView mContent;
    private TextView mTime;

    public ChatListViewHolder(View itemView) {
      super(itemView);
      mAvatar = itemView.findViewById(R.id.chat_avatar);
      mName = itemView.findViewById(R.id.chat_name);
      mContent = itemView.findViewById(R.id.chat_content);
      mTime = itemView.findViewById(R.id.chat_time);
    }

    public void bindData(ChatItem data) {
      mAvatar.setImageResource(data.getAvatarId());
      mName.setText(data.getName());
      mContent.setText(data.getContent());
      mTime.setText(data.getTime());
    }
  }
}
