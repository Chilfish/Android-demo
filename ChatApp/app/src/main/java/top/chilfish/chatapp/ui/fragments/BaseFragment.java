package top.chilfish.chatapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment<Binding extends ViewDataBinding> extends Fragment {
  protected Binding binding;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState
  ) {
    binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
    return binding.getRoot();
  }

  abstract protected int getLayoutId();

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

}
