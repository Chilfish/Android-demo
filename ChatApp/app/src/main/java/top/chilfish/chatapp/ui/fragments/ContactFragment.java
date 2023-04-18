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
import top.chilfish.chatapp.adapter.ContactAdapter;
import top.chilfish.chatapp.databinding.FragmentContactsBinding;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.ui.activities.ProfileActivity;

public class ContactFragment extends BaseFragment<FragmentContactsBinding> {
  private List<Profile> mContactList;

  public ContactFragment(List<Profile> contactList) {
    mContactList = contactList;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_contacts;
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

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
  }
}
