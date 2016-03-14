package org.ahomewithin.ahomewithin;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.util.SuccessChainListener;

import java.util.Map;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public class FirebaseClient {

    public static Firebase firebaseRef;
    private static Object lock = new Object();

    public static User getCurUser() {
        return curUser;
    }

    private static User curUser;

    public static String getCurPassword() {
        return curPassword;
    }

    private static String curPassword;
    private Firebase.AuthResultHandler authResultHandler;

    public FirebaseClient(
            Firebase.AuthResultHandler handler
    ) {
        authResultHandler = handler;
        getFirebaseRef();
    }

    public static Firebase getFirebaseRef() {
        if (firebaseRef == null) {
            synchronized (lock) {
                firebaseRef = new Firebase("https://ahomewithin.firebaseio.com/");
            }
        }
        return firebaseRef;
    }


    public static Firebase getUsersFirebaseRef() {
        return getFirebaseRef().child("users");
    }

    public void authenticate(final String email, String password) {
        getUsersFirebaseRef().authWithPassword(email, password, authResultHandler);
        curPassword = password;
    }

    public void changePassword(
            final Context context, final String newPassword, final SuccessChainListener listener) {
        if (curPassword.equals(newPassword)) {
            listener.run();
            return;
        }

        getFirebaseRef().changePassword(
                curUser.getEmail(), curPassword, newPassword,
                new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        curPassword = newPassword;
                        listener.run();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(
                                context,
                                "Change Password Failed : " + firebaseError.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    public void getCurrentUser(String email, final SuccessChainListener listener) {
        getUsersFirebaseRef()
                .orderByChild("email")
                .equalTo(email)
                .addChildEventListener(
                        new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                curUser = dataSnapshot.getValue(User.class);
                                listener.run();
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        }
                );
    }


    public void getNewUser(final Context context, final DialogInterface dialog,
                           final User user, String password) {
        getFirebaseRef().createUser(
                user.getEmail(),
                password,
                new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Toast.makeText(
                                context,
                                "Successfully created user account " + user.getName() + "; Please log in",
                                Toast.LENGTH_SHORT
                        ).show();

                        Firebase childRef = getUsersFirebaseRef()
                                .child(getChildFromEmail(user.getEmail()));
                        childRef.setValue(user);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(
                                context,
                                String.format(
                                        "%s; Please try again",
                                        firebaseError.getMessage()
                                ),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    private String getChildFromEmail(String email) {
        return email.replace('.', '@');
    }

    public void resetPassword(final Context context, final DialogInterface dialog, String email) {
        getFirebaseRef().resetPassword(
                email, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(
                                context,
                                "Reset email has been sent!",
                                Toast.LENGTH_SHORT
                        ).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(
                                context,
                                String.format(
                                        "%s !!! Please try again",
                                        firebaseError.getMessage()
                                ),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    public void updateUserInfo(final Context context, final DialogInterface dialog,
                               final User newUser, final String newPassword,
                               final SuccessChainListener extraActionListener) {
        if (newUser.getEmail().equals(curUser.getEmail())) {
            changePassword(context, newPassword,
                    new SuccessChainListener() {
                        @Override
                        public void run() {
                            getUsersFirebaseRef()
                                    .child(getChildFromEmail(curUser.getEmail()))
                                    .setValue(newUser);
                            extraActionListener.run();
                            dialog.dismiss();
                        }
                    });
        } else {
            removeUser(context,
                    new SuccessChainListener() {
                        @Override
                        public void run() {
                            getUsersFirebaseRef()
                                    .child(getChildFromEmail(curUser.getEmail()))
                                    .setValue(null);
                            getNewUser(context, dialog, newUser, newPassword);
                            extraActionListener.run();
                            curUser = newUser;
                            curPassword = newPassword;
                        }
                    });
        }
    }

    private void removeUser(final Context context, final SuccessChainListener listener) {
        getFirebaseRef().removeUser(
                curUser.getEmail(), curPassword, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        listener.run();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // error encountered
                        Toast.makeText(
                                context,
                                String.format(
                                        "%s; Please try again",
                                        firebaseError.getMessage()
                                ),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    public static boolean isUserLoggedIn() {
        double number = Math.random();
        if (number > 0.5) {
            return true;
        }
        return false;
    }
}
