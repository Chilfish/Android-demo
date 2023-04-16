package top.chilfish.chatapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.ChatItem;

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
    View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
    return new ChatListViewHolder(view);
  }

  @Override
  protected int getItemLayout() {
    return R.layout.item_chat;
  }

  public class ChatListViewHolder extends BaseAdapter<ChatItem>.ViewHolder {
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

    @Override
    public void bindData(ChatItem data) {
      try {
        Glide.with(itemView.getContext())
            .load(data.getProfile().getAvatar())
            .into(mAvatar);

        mName.setText(data.getProfile().getName());
        mContent.setText(data.getContent());
        mTime.setText(data.getTime());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
