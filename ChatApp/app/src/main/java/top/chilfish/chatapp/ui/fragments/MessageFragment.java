package top.chilfish.chatapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.adapter.MessageAdapter;
import top.chilfish.chatapp.entity.Message;
import top.chilfish.chatapp.ui.activities.ChatMainActivity;

import java.util.List;

public class MessageFragment extends Fragment implements ChatMainActivity.SendMessage {
  private List<Message> mMessages;
  private RecyclerView recyclerView;
  private MessageAdapter adapter;

  public MessageFragment(List<Message> data) {
    mMessages = data;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_message_list, container, false);

    Context context = view.getContext();

    recyclerView = (RecyclerView) view;
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    adapter = new MessageAdapter(mMessages);

    recyclerView.setAdapter(adapter);
    recyclerView.scrollToPosition(mMessages.size() - 1);
    return view;
  }

  @Override
  public void onSendMessage(Message message) {
    mMessages.add(message);
    adapter.notifyItemChanged(mMessages.size() - 1);
    recyclerView.scrollToPosition(mMessages.size() - 1);
  }
}
