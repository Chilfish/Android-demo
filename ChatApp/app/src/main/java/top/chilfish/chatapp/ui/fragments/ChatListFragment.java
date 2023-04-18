package top.chilfish.chatapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.adapter.ChatListAdapter;
import top.chilfish.chatapp.databinding.FragmentChatListsBinding;
import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.ui.activities.ChatMainActivity;

public class ChatListFragment extends BaseFragment<FragmentChatListsBinding> {
  private List<ChatItem> mChats;

  public ChatListFragment(List<ChatItem> data) {
    mChats = data;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_chat_lists;
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Context context = view.getContext();
    RecyclerView recyclerView = (RecyclerView) view;
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    var adapter = new ChatListAdapter(mChats);

    adapter.setOnItemClickListener(chatItem -> {
      Bundle bundle = new Bundle();

      bundle.putSerializable("profile", chatItem.getProfile());

      Intent intent = new Intent(getActivity(), ChatMainActivity.class);
      intent.putExtras(bundle);
      startActivity(intent);
    });

    recyclerView.setAdapter(adapter);
  }
}
