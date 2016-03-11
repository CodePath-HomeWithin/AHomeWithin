package org.ahomewithin.ahomewithin.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.FirebaseClient;
import org.ahomewithin.ahomewithin.fragments.LoginEditProfileDialogFragment;
import org.ahomewithin.ahomewithin.util.CustomStyle;
import org.ahomewithin.ahomewithin.util.SuccessChainListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public class ProfileActivity extends AppCompatActivity {

  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.tvUserName)
  TextView tvUserName;
  @Bind(R.id.tvEmail)
  TextView tvEmail;
  @Bind(R.id.tvPhone)
  TextView tvPhone;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setProfileContent();
  }

  private void setProfileContent() {
    User curUser = FirebaseClient.getCurUser();
    if(curUser != null) {
      setProfileContent(curUser);
    }
  }

  private void setProfileContent(User curUser) {
    int customColor = R.color.colorPrimaryDark;
    tvUserName.setText(
        CustomStyle.stylizeFirstPart("User Name  : ", curUser.getName(), customColor)
    );
    tvEmail.setText(
        CustomStyle.stylizeFirstPart("Email Addr : ", curUser.getEmail(), customColor)
    );
    tvPhone.setText(
        CustomStyle.stylizeFirstPart("Phone Num  : ", curUser.getPhone(), customColor)
    );
  }

  @OnClick(R.id.btnEdit)
  public void editProfile() {
    LoginEditProfileDialogFragment dialogFragment =
        LoginEditProfileDialogFragment.newInstance(
            new User.OnCreateUserListener() {
              @Override
              public void onCreateUserListener(DialogInterface dialog, final User newUser, String newPassword) {
                UserActivity.getFirebaseClient().updateUserInfo(
                    getApplicationContext(), dialog, newUser, newPassword,
                    new SuccessChainListener() {
                      @Override
                      public void run() {
                        setProfileContent(newUser);
                      }
                    }
                );
              }
            }
        );
    FragmentManager fm = getSupportFragmentManager();
    dialogFragment.show(fm, "Edit Profile");
  }

}
