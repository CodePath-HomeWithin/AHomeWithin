package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;

import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.FirebaseClient;

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
    User curUser = FirebaseClient.getCurUser();
    etUserName.setText(curUser.getName());
    etUserName.setSelection(curUser.getName().length());
    etPassword.setText(FirebaseClient.getCurPassword());
    etPassword.setSelection(FirebaseClient.getCurPassword().length());
    etEmail.setText(curUser.getEmail());
    etEmail.setSelection(curUser.getEmail().length());
    etPhone.setText(curUser.getPhone());
    etPhone.setSelection(curUser.getPhone().length());
  }

}
