package top.chilfish.chatapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.adapter.MessageAdapter;
import top.chilfish.chatapp.databinding.FragmentMessageListBinding;
import top.chilfish.chatapp.entity.Message;
import top.chilfish.chatapp.ui.activities.ChatMainActivity;

public class MessageFragment extends BaseFragment<FragmentMessageListBinding> implements ChatMainActivity.OnSendMessage {
  private List<Message> mMessages;
  private RecyclerView recyclerView;
  private MessageAdapter adapter;

  public MessageFragment(List<Message> data) {
    mMessages = data;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_message_list;
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Context context = view.getContext();

    recyclerView = (RecyclerView) view;
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    adapter = new MessageAdapter(mMessages);

    recyclerView.setAdapter(adapter);
    recyclerView.scrollToPosition(mMessages.size() - 1);

  }

  @Override
  public void onSendMessage(Context context, Message message) {
    mMessages.add(message);
    adapter.notifyItemChanged(mMessages.size() - 1);
    recyclerView.scrollToPosition(mMessages.size() - 1);
  }
}
