package org.ahomewithin.ahomewithin.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.ahomewithin.ahomewithin.CredentialView;
import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.util.LoginCallback;
import org.ahomewithin.ahomewithin.util.OnResetPasswordListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiangyang_xiao on 3/19/16.
 */
public class LoginFragment extends Fragment {
    public static final String FRAGMENT_TAG = LoginFragment.class.getSimpleName();

    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.tilEmail)
    TextInputLayout tilEmail;
    @Bind(R.id.tilPassword)
    TextInputLayout tilPassword;

    private LoginCallback mCallback;
    private ParseClient mClient;

    public static LoginFragment newInstance(
        int requestCode,
        Parcelable... extra
        ) {
        LoginFragment userFragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt("requestCode", requestCode);
        if(extra.length > 0) {
            args.putParcelableArray("extra", extra);
        }
        userFragment.setArguments(args);
        return userFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, loginView);
        setListeners();
        return loginView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mClient = ParseClient.newInstance(context);
        mCallback = (LoginCallback)context;
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
                            //Can not use setErrorEnabled(false) due to
                            //error messages from 2nd will be missing
                            //acording to https://code.google.com/p/android/issues/detail?id=190355
                            //etCred.getTextInputLayout().setErrorEnabled(false);
                            etCred.getTextInputLayout().setError(null);
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
            // thing is no need to setErrorEnabled(true).
            // when you implement seterror method to non-null string,
            // setErrorEnabled(true) called automatically.
            tilEmail.setError("User name can not be empty");
            return;
        }
        final String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Password can not be empty");
            return;
        }
        mClient.login(
            email, password, new ParseClientAsyncHandler() {
                @Override
                public void onSuccess(Object obj) {
                    Toast.makeText(
                        getContext(),
                        "Successfully logged in",
                        Toast.LENGTH_SHORT).show();
                    Bundle args = getArguments();
                    if(args.containsKey("extra")) {
                        mCallback.onPostLogin(
                            args.getInt("requestCode"),
                            args.getParcelableArray("extra")
                        );
                    } else {
                        mCallback.onPostLogin(args.getInt("requestCode"));
                    }
                }

                @Override
                public void onFailure(String error) {
                    tilPassword.setError("Name and Password do not match our record!!!");
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
                    mClient.signup(user, password,
                        new ParseClientAsyncHandler() {
                            @Override
                            public void onSuccess(Object obj) {
                                Toast.makeText(
                                    getContext(),
                                    "Successfully created user account " + user.lastName + "; Please log in",
                                    Toast.LENGTH_SHORT
                                ).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(String error) {
                                Toast.makeText(
                                    getContext(),
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
        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager, "Create a new account");
    }

    @OnClick(R.id.tvForgetPasswd)
    public void resetPassword() {
        LoginResetPasswordDialogFragment fragment = LoginResetPasswordDialogFragment.newInstance(
            new OnResetPasswordListener() {
                @Override
                public void onResetPasswordListener(final DialogInterface dialog, String email) {
                    mClient.requestResetPassword(email,
                        new ParseClientAsyncHandler() {
                            @Override
                            public void onSuccess(Object obj) {
                                Toast.makeText(
                                    getContext(),
                                    "Reset email has been sent!",
                                    Toast.LENGTH_SHORT
                                ).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(String error) {
                                Toast.makeText(
                                    getContext(),
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
        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager, "Reset Password");
    }
}
