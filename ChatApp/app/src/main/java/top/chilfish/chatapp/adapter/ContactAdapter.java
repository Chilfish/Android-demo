package top.chilfish.chatapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.chilfish.chatapp.databinding.ItemContactBinding;
import top.chilfish.chatapp.entity.Profile;

public class ContactAdapter extends BaseAdapter<Profile> {
  private List<Profile> mContactList;

  public ContactAdapter(List<Profile> dataList) {
    super(dataList);
    mContactList = dataList;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemContactBinding binding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()),
        parent, false);

    return new ContactViewHolder(binding);
  }

  public class ContactViewHolder extends BaseAdapter<Profile>.ViewHolder {
    private ItemContactBinding binding;

    public ContactViewHolder(ItemContactBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    @Override
    public void bindData(Profile data) {
      try {
        Glide.with(itemView.getContext())
            .load(data.getAvatar())
            .into(binding.contactAvatar);

        binding.contactName.setText(data.getName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
