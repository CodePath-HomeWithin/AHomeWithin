package org.ahomewithin.ahomewithin.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.LoginEditProfileDialogFragment;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.util.CustomStyle;

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
  @Bind(R.id.tvDesp)
  TextView tvDesp;
  @Bind(R.id.tvType)
  TextView tvType;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setProfileContent();
  }

  private void setProfileContent() {
    User curUser = User.getCurrentUser();
    if (curUser != null) {
      setProfileContent(curUser);
    }
  }

  private void setProfileContent(User curUser) {
    int customColor = R.color.colorPrimaryDark;
    tvUserName.setText(
        CustomStyle.stylizeFirstPart("User  Name  : ", curUser.name, customColor)
    );
    tvEmail.setText(
        CustomStyle.stylizeFirstPart("Email  Addr : ", curUser.email, customColor)
    );
    tvPhone.setText(
        CustomStyle.stylizeFirstPart("Phone  Num  : ", curUser.phone, customColor)
    );
    tvDesp.setText(
        CustomStyle.stylizeFirstPart("Description : ", curUser.desp, customColor)
    );
    tvType.setText(
        CustomStyle.stylizeFirstPart("User  Type  : ", curUser.type, customColor)
    );
  }

  @OnClick(R.id.btnEdit)
  public void editProfile() {
    LoginEditProfileDialogFragment dialogFragment =
        LoginEditProfileDialogFragment.newInstance(
            new User.OnCreateUserListener() {
              @Override
              public void onCreateUserListener(final DialogInterface dialog, final User newUser, String newPassword) {
                ParseClient.newInstance(getApplicationContext())
                    .updateUserInfo(
                        newUser,
                        newPassword,
                        new ParseClientAsyncHandler() {
                          @Override
                          public void onSuccess(Object obj) {
                            setProfileContent(newUser);
                            dialog.dismiss();
                          }

                          @Override
                          public void onFailure(String error) {
                            Toast.makeText(
                                getApplicationContext(),
                                error,
                                Toast.LENGTH_SHORT
                            ).show();
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
