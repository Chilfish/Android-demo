package com.chill.learn.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chill.learn.R;
import com.chill.learn.adapter.MessageAdapter;
import com.chill.learn.entity.Message;

import java.util.List;

public class MessageFragment extends Fragment {
  private List<Message> mMessages;

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

    RecyclerView recyclerView = (RecyclerView) view;
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    var adapter = new MessageAdapter(mMessages);
    recyclerView.setAdapter(adapter);
    return view;
  }
}
