package top.chilfish.chatapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.adapter.ChatListAdapter;
import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.ui.activities.ChatMainActivity;

public class ChatListFragment extends Fragment {
  private List<ChatItem> mChats;

  public ChatListFragment(List<ChatItem> data) {
    mChats = data;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_chat_lists, container, false);

    Context context = view.getContext();
    RecyclerView recyclerView = (RecyclerView) view;
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    var adapter = new ChatListAdapter(mChats);

    adapter.setOnItemClickListener(chatItem -> {
      String name = chatItem.getName();
      String uid = chatItem.getUid();
      String avatar = chatItem.getAvatar();

      Bundle bundle = new Bundle();
      bundle.putString("chatName", name);
      bundle.putString("chatUid", uid);
      bundle.putString("chatAvatar", avatar);

      Intent intent = new Intent(getActivity(), ChatMainActivity.class);
      intent.putExtras(bundle);
      startActivity(intent);
    });

    recyclerView.setAdapter(adapter);
    return view;
  }
}
