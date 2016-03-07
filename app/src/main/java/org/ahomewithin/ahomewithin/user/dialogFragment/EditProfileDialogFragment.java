package org.ahomewithin.ahomewithin.user.dialogFragment;

import android.os.Bundle;

import org.ahomewithin.ahomewithin.user.FirebaseClient;
import org.ahomewithin.ahomewithin.user.OnCreateUserListener;
import org.ahomewithin.ahomewithin.user.User;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public class EditProfileDialogFragment extends CreateUserDialogFragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public static EditProfileDialogFragment newInstance(
      OnCreateUserListener listener
  ) {
    EditProfileDialogFragment fragment = new EditProfileDialogFragment();
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
