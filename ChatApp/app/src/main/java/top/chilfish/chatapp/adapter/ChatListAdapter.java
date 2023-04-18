package top.chilfish.chatapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.databinding.ItemChatBinding;
import top.chilfish.chatapp.entity.ChatItem;

public class ChatListAdapter extends BaseAdapter<ChatItem> {
  private List<ChatItem> mChatList;

  public ChatListAdapter(List<ChatItem> data) {
    super(data);
    mChatList = data;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemChatBinding binging = ItemChatBinding.inflate(LayoutInflater.from(parent.getContext()),
        parent, false);

    return new ChatListViewHolder(binging);
  }

  public class ChatListViewHolder extends BaseAdapter<ChatItem>.ViewHolder {
    private ItemChatBinding binding;

    public ChatListViewHolder(ItemChatBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    @Override
    public void bindData(ChatItem data) {
      try {
        Glide.with(itemView.getContext())
            .load(data.getProfile().getAvatar())
            .into(binding.chatAvatar);

        binding.chatName.setText(data.getProfile().getName());
        binding.chatContent.setText(data.getContent());
        binding.chatTime.setText(data.getTime());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
