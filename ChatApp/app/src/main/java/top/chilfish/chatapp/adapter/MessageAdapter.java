package top.chilfish.chatapp.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.databinding.ItemMessageBinding;
import top.chilfish.chatapp.entity.Message;
import top.chilfish.chatapp.helper.TimeFormat;

import java.util.List;

public class MessageAdapter extends BaseAdapter<Message> {
  private List<Message> mMessageList;

  private static final String TAG = "MessageAdapter";

  public MessageAdapter(List<Message> dataList) {
    super(dataList);
    mMessageList = dataList;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemMessageBinding binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()),
        parent, false);
    return new MessageViewHolder(binding);
  }

  public class MessageViewHolder extends BaseAdapter<Message>.ViewHolder {
    private ItemMessageBinding binding;

    public MessageViewHolder(ItemMessageBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    @Override
    public void bindData(Message data) {
      binding.messText.setText(data.getContent());
      binding.messTime.setText(TimeFormat.toString(data.getTime(), "HH:mm"));
      binding.timeBubble.setText(TimeFormat.toString(data.getTime(), "MM-dd HH:mm"));

      LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.messBody.getLayoutParams();
      if (data.isRight()) {
        layoutParams.gravity = Gravity.END;
        binding.messBody.setBackgroundResource(R.drawable.message_r);
      } else {
        layoutParams.gravity = Gravity.START;
        binding.messBody.setBackgroundResource(R.drawable.message_l);
      }

      // 时间泡泡：第一条消息要加、否则与上一条消息的时间间隔大于5分钟才加
      if (mMessageList.get(0).equals(data)) {
        binding.timeBubble.setVisibility(View.VISIBLE);
        return;
      }
      int index = mMessageList.indexOf(data);
      Message lastMessage = mMessageList.get(index - 1);

      if (data.getTime() - lastMessage.getTime() >= 5 * 60 * 1000) {
        binding.timeBubble.setVisibility(View.VISIBLE);
      } else {
        binding.timeBubble.setVisibility(View.GONE);
      }
    }
  }
}
