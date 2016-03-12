package org.ahomewithin.ahomewithin.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.util.OnResetPasswordListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public class LoginResetPasswordDialogFragment extends DialogFragment {
  @Bind(R.id.etEmail)
  EditText etEmail;
  @Bind(R.id.tilEmail)
  TextInputLayout tilEmail;

  public static LoginResetPasswordDialogFragment newInstance(
      OnResetPasswordListener listener
  ) {
    LoginResetPasswordDialogFragment fragment = new LoginResetPasswordDialogFragment();
    Bundle args = new Bundle();
    args.putSerializable("listener", listener);
    fragment.setArguments(args);
    return fragment;
  }


  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    View view = LayoutInflater.from(getActivity())
        .inflate(R.layout.fragment_reset_password, null);
    ButterKnife.bind(this, view);
    etEmail.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
              tilEmail.setErrorEnabled(false);
            }
          }
        }
    );

    AlertDialog resetDialog = new AlertDialog.Builder(getActivity())
        .setView(view)
        .setPositiveButton(
            "Reset",
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

    resetDialog.setCanceledOnTouchOutside(false);

    return resetDialog;
  }

  @Override
  public void onStart() {
    super.onStart();
    final AlertDialog alertDialog = (AlertDialog) getDialog();
    if (alertDialog != null) {
      Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
      final OnResetPasswordListener listener =
          (OnResetPasswordListener) getArguments().getSerializable("listener");

      positiveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String email = etEmail.getText().toString();
          if (!TextUtils.isEmpty(email)) {
            listener.onResetPasswordListener(alertDialog, email);
            return;
          }
          tilEmail.setErrorEnabled(true);
          tilEmail.setError("Email address can not be empty!");
        }
      });
    }

  }
}
