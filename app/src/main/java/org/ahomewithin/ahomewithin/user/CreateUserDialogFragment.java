package org.ahomewithin.ahomewithin.user;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ahomewithin.ahomewithin.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 3/5/16.
 */
public class CreateUserDialogFragment extends DialogFragment {

  @Bind(R.id.etUserName)
  EditText etUserName;
  @Bind(R.id.etPassword)
  EditText etPassword;
  @Bind(R.id.etEmail)
  EditText etEmail;
  @Bind(R.id.etPhone)
  EditText etPhone;

  public static CreateUserDialogFragment newInstance(
      OnCreateUserListener listener
  ) {
    CreateUserDialogFragment fragment = new CreateUserDialogFragment();
    Bundle args = new Bundle();
    args.putSerializable("listener", listener);
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    View view = LayoutInflater.from(getActivity())
        .inflate(R.layout.fragment_join, null);
    ButterKnife.bind(this, view);

    setViewContent();

    Dialog createUserDialog = new AlertDialog.Builder(getActivity())
        .setView(view)
        .setPositiveButton(
            "Save",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

              }
            }
        )
        .setNegativeButton(
            "Cancel",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
              }
            }
        )
        .setCancelable(false)
        .create();
    createUserDialog.setCanceledOnTouchOutside(false);
    return createUserDialog;
  }

  protected void setViewContent() {
  }

  @Override
  public void onStart() {
    super.onStart();
    final AlertDialog alertDialog = (AlertDialog) getDialog();
    if (alertDialog != null) {
      Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
      final OnCreateUserListener listener =
          (OnCreateUserListener) getArguments().getSerializable("listener");

      positiveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          User newUser = new User(
              etUserName.getText().toString(),
              etEmail.getText().toString(),
              etPhone.getText().toString()
          );
          listener.onCreateUserListener(alertDialog, newUser, etPassword.getText().toString());
        }
      });
    }
  }
}
