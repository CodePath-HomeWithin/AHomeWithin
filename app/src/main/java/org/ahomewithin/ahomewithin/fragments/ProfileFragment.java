package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.util.CustomStyle;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public class ProfileFragment extends Fragment {

    public static final String FRAGMENT_TAG = ProfileFragment.class.getSimpleName();


    @Bind(R.id.tvUserName)
    TextView tvUserName;
    @Bind(R.id.tvEmail)
    TextView tvEmail;
    @Bind(R.id.tvPhone)
    TextView tvPhone;
    // Not used yet
//    @Bind(R.id.tvDesp)
//    TextView tvDesp;
//    @Bind(R.id.tvType)
//    TextView tvType;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, convertView);

        getActivity().getActionBar().setTitle("Profile");

        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
                CustomStyle.stylizeFirstPart("User  Name  : ", curUser.lastName, customColor)
        );
        tvEmail.setText(
                CustomStyle.stylizeFirstPart("Email  Addr : ", curUser.email, customColor)
        );
        tvPhone.setText(
                CustomStyle.stylizeFirstPart("Phone  Num  : ", curUser.phone, customColor)
        );
//        tvDesp.setText(
//                CustomStyle.stylizeFirstPart("Description : ", curUser.description, customColor)
//        );
//        // TODO fix this
//        tvType.setText(
//                // CustomStyle.stylizeFirstPart("User  Type  : ", curUser.type, customColor)
//                CustomStyle.stylizeFirstPart("User  Type  : ", "FIX THIS", customColor)
//        );
    }

//    @OnClick(R.id.btnEdit)
//    public void editProfile() {
//        LoginEditProfileDialogFragment dialogFragment =
//                LoginEditProfileDialogFragment.newInstance(
//                        new User.OnCreateUserListener() {
//                            @Override
//                            public void onCreateUserListener(final DialogInterface dialog, final User newUser, String newPassword) {
//                                ParseClient.newInstance(getActivity())
//                                        .updateUserInfo(
//                                                newUser,
//                                                newPassword,
//                                                new ParseClientAsyncHandler() {
//                                                    @Override
//                                                    public void onSuccess(Object obj) {
//                                                        setProfileContent(newUser);
//                                                        dialog.dismiss();
//                                                    }
//
//                                                    @Override
//                                                    public void onFailure(String error) {
//                                                        Toast.makeText(
//                                                                getActivity(),
//                                                                error,
//                                                                Toast.LENGTH_SHORT
//                                                        ).show();
//                                                    }
//                                                }
//                                        );
//                            }
//                        }
//                );
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        dialogFragment.show(fm, "Edit Profile");
//    }

}
