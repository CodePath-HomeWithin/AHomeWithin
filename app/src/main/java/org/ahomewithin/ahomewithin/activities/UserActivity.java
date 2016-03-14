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

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.ahomewithin.ahomewithin.CredentialView;
import org.ahomewithin.ahomewithin.FirebaseClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.LoginCreateUserDialogFragment;
import org.ahomewithin.ahomewithin.fragments.LoginResetPasswordDialogFragment;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.util.OnResetPasswordListener;
import org.ahomewithin.ahomewithin.util.SuccessChainListener;

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

    private static FirebaseClient firebaseClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        String message = getIntent().getStringExtra("Message");
        if ((message != null) || (message != "")){
            tvUserLoginAdvice.setText(message);
        }

        Firebase.setAndroidContext(this);
        setFirebaseClient();
        setListeners();
    }

    private void setFirebaseClient() {
        firebaseClient = new FirebaseClient(
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Successfully logged in",
                                Toast.LENGTH_SHORT).show();
                        firebaseClient.getCurrentUser(getEmail(),
                                new SuccessChainListener() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        tilPassword.setErrorEnabled(true);
                        tilPassword.setError("User name and Password do not match our record!!!");
                    }
                }
        );
    }

    public static FirebaseClient getFirebaseClient() {
        return firebaseClient;
    }

    private String getEmail() {
        return etEmail.getText().toString();
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
        firebaseClient.authenticate(email, password);
    }

    @OnClick(R.id.btnJoin)
    public void addNewUser() {
        LoginCreateUserDialogFragment fragment = LoginCreateUserDialogFragment.newInstance(
                new User.OnCreateUserListener() {
                    @Override
                    public void onCreateUserListener(DialogInterface dialog, final User user, String password) {
                        firebaseClient.getNewUser(getApplicationContext(), dialog, user, password);
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
                    public void onResetPasswordListener(DialogInterface dialog, String email) {
                        firebaseClient.resetPassword(getApplicationContext(), dialog, email);
                    }
                }
        );
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, "Reset Password");
    }

}
