package org.ahomewithin.ahomewithin.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ahomewithin.ahomewithin.CredentialView;
import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.LoginCreateUserDialogFragment;
import org.ahomewithin.ahomewithin.fragments.LoginResetPasswordDialogFragment;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.util.OnResetPasswordListener;
import org.ahomewithin.ahomewithin.util.UserTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity {
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.tilEmail)
    TextInputLayout tilEmail;
    @Bind(R.id.tilPassword)
    TextInputLayout tilPassword;
    @Bind(R.id.tvUserLoginAdvice) TextView tvUserLoginAdvice;

    private static ParseClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        client = ParseClient.newInstance(this);
        setListeners();
        String message = getIntent().getStringExtra("Message");
        if (message != null && message.equals("")) {
            tvUserLoginAdvice.setText(message);
        }
    }

    private void setListeners() {
        CredentialView[] etCredentials = {
                new CredentialView(etEmail, tilEmail),
                new CredentialView(etPassword, tilPassword)};

        for (final CredentialView etCred : etCredentials) {
            etCred.getEditText().clearFocus();
            etCred.getEditText().addTextChangedListener(
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
                                etCred.getTextInputLayout().setErrorEnabled(false);
                            }

                        }
                    }
            );

        }
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        final String email = etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError("User name can not be empty");
            return;
        }
        final String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            tilPassword.setErrorEnabled(true);
            tilPassword.setError("Password can not be empty");
            return;
        }
        client.login(
                email, password, new ParseClientAsyncHandler() {
                    @Override
                    public void onSuccess(Object obj) {
                        UserTools.loginUser(UserActivity.this, email);
                        Toast.makeText(
                                getApplicationContext(),
                                "Successfully logged in",
                                Toast.LENGTH_SHORT).show();
                        Intent data = new Intent();
                        setResult(RESULT_OK, data);
                        finish();
                    }

                    @Override
                    public void onFailure(String error) {
                        UserTools.logoutUser(UserActivity.this, email);
                        tilPassword.setErrorEnabled(true);
                        tilPassword.setError("ParseObjectUser name and Password do not match our record!!!");
                    }
                }
        );
    }

    @OnClick(R.id.btnJoin)
    public void addNewUser() {
        LoginCreateUserDialogFragment fragment = LoginCreateUserDialogFragment.newInstance(
                new User.OnCreateUserListener() {
                    @Override
                    public void onCreateUserListener(final DialogInterface dialog, final User user, String password) {
                        client.signup(user, password,
                                new ParseClientAsyncHandler() {
                                    @Override
                                    public void onSuccess(Object obj) {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Successfully created user account " + user.lastName + "; Please log in",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                String.format(
                                                        "%s; Please try again",
                                                        error
                                                ),
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                });
                    }
                }
        );
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, "Create a new account");
    }

    @OnClick(R.id.tvForgetPasswd)
    public void resetPassword() {
        LoginResetPasswordDialogFragment fragment = LoginResetPasswordDialogFragment.newInstance(
                new OnResetPasswordListener() {
                    @Override
                    public void onResetPasswordListener(final DialogInterface dialog, String email) {
                        client.requestResetPassword(email,
                                new ParseClientAsyncHandler() {
                                    @Override
                                    public void onSuccess(Object obj) {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Reset email has been sent!",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                String.format(
                                                        "%s !!! Please try again",
                                                        error
                                                ),
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                });
                    }
                }
        );
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, "Reset Password");
    }

}
