package top.chilfish.chatapp.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import top.chilfish.chatapp.R;
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

  @Override
  protected int getItemLayout() {
    return R.layout.item_message;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
    return new MessageViewHolder(view);
  }

  public class MessageViewHolder extends BaseAdapter<Message>.ViewHolder {
    TextView mTimeBubble;
    TextView mContent;
    TextView mTime;
    LinearLayout mBody;

    public MessageViewHolder(View itemView) {
      super(itemView);
      mTimeBubble = itemView.findViewById(R.id.time_bubble);
      mContent = itemView.findViewById(R.id.mess_text);
      mTime = itemView.findViewById(R.id.mess_time);
      mBody = itemView.findViewById(R.id.mess_body);
    }

    @Override
    public void bindData(Message data) {
      mContent.setText(data.getContent());
      mTime.setText(TimeFormat.toString(data.getTime(), "HH:mm"));
      mTimeBubble.setText(TimeFormat.toString(data.getTime(), "MM-dd HH:mm"));

      LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mBody.getLayoutParams();
      if (data.isRight()) {
        layoutParams.gravity = Gravity.END;
        mBody.setBackgroundResource(R.drawable.message_r);
      } else {
        layoutParams.gravity = Gravity.START;
        mBody.setBackgroundResource(R.drawable.message_l);
      }

      // 时间泡泡：第一条消息要加、否则与上一条消息的时间间隔大于5分钟才加
      if (mMessageList.get(0).equals(data)) {
        mTimeBubble.setVisibility(View.VISIBLE);
        return;
      }
      int index = mMessageList.indexOf(data);
      Message lastMessage = mMessageList.get(index - 1);

      if (data.getTime() - lastMessage.getTime() >= 5 * 60 * 1000) {
        mTimeBubble.setVisibility(View.VISIBLE);
      } else {
        mTimeBubble.setVisibility(View.GONE);
      }
    }
  }
}
