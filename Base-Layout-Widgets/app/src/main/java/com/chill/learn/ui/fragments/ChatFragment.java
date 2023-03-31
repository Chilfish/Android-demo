package com.chill.learn.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.R;
import com.chill.learn.adapter.ChatListAdapter;
import com.chill.learn.entity.ChatItem;

import java.util.List;

public class ChatFragment extends Fragment {
  private List<ChatItem> mChats;

  public ChatFragment(List<ChatItem> data) {
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
      Bundle bundle = new Bundle();
      bundle.putString("name", name);

    });

    recyclerView.setAdapter(adapter);
    return view;
  }
}
