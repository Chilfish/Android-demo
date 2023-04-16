package top.chilfish.chatapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.Profile;

public class ContactAdapter extends BaseAdapter<Profile> {
  private List<Profile> mContactList;

  public ContactAdapter(List<Profile> dataList) {
    super(dataList);
    mContactList = dataList;
  }

  @Override
  protected int getItemLayout() {
    return R.layout.item_contact;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
    return new ContactViewHolder(view);
  }

  public class ContactViewHolder extends BaseAdapter<Profile>.ViewHolder {
    private ImageView mAvatar;
    private TextView mName;

    public ContactViewHolder(@NonNull View itemView) {
      super(itemView);
      mAvatar = itemView.findViewById(R.id.contact_avatar);
      mName = itemView.findViewById(R.id.contact_name);
    }

    @Override
    public void bindData(Profile data) {
      try {
        Glide.with(itemView.getContext())
            .load(data.getAvatar())
            .into(mAvatar);

        mName.setText(data.getName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
