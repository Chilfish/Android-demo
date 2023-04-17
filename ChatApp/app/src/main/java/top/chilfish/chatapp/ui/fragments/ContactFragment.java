package top.chilfish.chatapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.adapter.ContactAdapter;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.ui.activities.ChatMainActivity;
import top.chilfish.chatapp.ui.activities.ProfileActivity;

public class ContactFragment extends Fragment {
  private List<Profile> mContactList;

  public ContactFragment(List<Profile> contactList) {
    mContactList = contactList;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_contacts, container, false);

    Context context = view.getContext();
    RecyclerView recyclerView = (RecyclerView) view;
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    var adapter = new ContactAdapter(mContactList);

    // jump to profile page
    adapter.setOnItemClickListener(profile -> {
      Bundle bundle = new Bundle();
      bundle.putSerializable("profile", profile);

      Intent intent = new Intent(getContext(), ProfileActivity.class);
      intent.putExtras(bundle);
      startActivity(intent);
    });

    recyclerView.setAdapter(adapter);
    return view;
  }
}