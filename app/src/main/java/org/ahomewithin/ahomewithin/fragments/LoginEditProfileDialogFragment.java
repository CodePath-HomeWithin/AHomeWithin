package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.widget.SpinnerAdapter;

import org.ahomewithin.ahomewithin.models.User;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public class LoginEditProfileDialogFragment extends LoginCreateUserDialogFragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public static LoginEditProfileDialogFragment newInstance(
      User.OnCreateUserListener listener
  ) {
    LoginEditProfileDialogFragment fragment = new LoginEditProfileDialogFragment();
    Bundle args = new Bundle();
    args.putSerializable("listener", listener);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected void setViewContent() {
    User curUser = User.getCurrentUser();
    etUserName.setText(curUser.name);
    etUserName.setSelection(curUser.name.length());
    etPassword.setText(User.PWD_HOLDER);
    etPassword.setSelection(User.PWD_HOLDER.length());
    etEmail.setText(curUser.email);
    etEmail.setSelection(curUser.email.length());
    etPhone.setText(curUser.phone);
    etPhone.setSelection(curUser.phone.length());
    etDesp.setText(curUser.desp);
    etDesp.setSelection(curUser.desp.length());

    SpinnerAdapter adapter = spUserType.getAdapter();
    int index = 0;
    for(int idx=0; idx<adapter.getCount(); idx++) {
      if(adapter.getItem(idx).equals(curUser.type)) {
        index = idx;
        break;
      }
    }
    spUserType.setSelection(index);

  }

}
