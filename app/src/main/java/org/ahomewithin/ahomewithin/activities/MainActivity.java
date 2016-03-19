package org.ahomewithin.ahomewithin.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.AboutUsFragment;
import org.ahomewithin.ahomewithin.fragments.EventsFragment;
import org.ahomewithin.ahomewithin.fragments.HomeFragment;
import org.ahomewithin.ahomewithin.fragments.LearnMoreFragment;
import org.ahomewithin.ahomewithin.fragments.MapFragment;
import org.ahomewithin.ahomewithin.fragments.StreamPagerFragment;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nvView)
    NavigationView nvView;
    private Drawer drawer;
    private AccountHeader accountHeader;
    private ParseClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = ParseClient.newInstance(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }


        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        HomeFragment homeFragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, homeFragment)
            .addToBackStack("home")
            .commit();

        createDrawer();

        if (!isOnline()) {
            showSnackbar("Oops!  Please check internet connection!");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        // The action bar home/up action should open or close the drawer.
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                dlDrawer.openDrawer(GravityCompat.START);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showSnackbar(String message) {
        final Snackbar snackBar = Snackbar.make(findViewById(R.id.dlDrawer),
            message, Snackbar.LENGTH_INDEFINITE);

        snackBar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }

    public void onSelectLibrary(View v) {
        Fragment streamPagerFragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.LIBRARY, true);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, streamPagerFragment)
            .addToBackStack("library")
            .commit();
    }

    public void onSelectNearYou(View v) {
        Fragment mapFragment = MapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, mapFragment)
            .addToBackStack("map")
            .commit();
    }

    public void onSelectToolsAndTechniques(View v) {
        Fragment streamPagerFragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.STORE, false);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, streamPagerFragment)
            .addToBackStack("store")
            .commit();
    }

    private void createDrawer() {
        accountHeader = createAccountHeader();
        //create the drawer and remember the `Drawer` result object
        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withName(R.string.home);
        PrimaryDrawerItem myLibraryItem = new PrimaryDrawerItem().withName(R.string.library);
        PrimaryDrawerItem trainingItem = new PrimaryDrawerItem().withName(R.string.training_and_tools);
        PrimaryDrawerItem eventsItem = new PrimaryDrawerItem().withName(R.string.events);
        PrimaryDrawerItem learnMoreItem = new PrimaryDrawerItem().withName(R.string.learn_more);
        PrimaryDrawerItem aboutUsItem = new PrimaryDrawerItem().withName(R.string.about_us);
        PrimaryDrawerItem settingsItem = new PrimaryDrawerItem().withName(R.string.action_settings);
        PrimaryDrawerItem chatRoomItem = new PrimaryDrawerItem().withName(R.string.chat);
        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem().withName(R.string.logout);

        // do something with the clicked item :D
        //fragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.LIBRARY);
        drawer = new DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withAccountHeader(accountHeader)
            .addDrawerItems(
                homeItem,
                new DividerDrawerItem(),
                myLibraryItem,
                trainingItem,
                eventsItem,
                learnMoreItem,
                aboutUsItem,
                new DividerDrawerItem(),
                //settingsItem,
                chatRoomItem,
                logoutItem)
            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    // do something with the clicked item :D
                    Fragment fragment = null;
                    Log.d("DEBUG", "Clicked item nº" + position);
                    switch (position) {
                        case 1:
                            fragment = HomeFragment.newInstance();
                            break;
                        case 3:
                            fragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.LIBRARY, true);
                            break;
                        case 4:
                            fragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.STORE, false);
                            break;
                        case 5:
                            fragment = EventsFragment.newInstance();
                            break;
                        case 6:
                            fragment = LearnMoreFragment.newInstance();
                            break;
                        case 7:
                            fragment = AboutUsFragment.newInstance();
                            break;

                        case 9:
                            Intent intent = new Intent(MainActivity.this, ChatRoomActivity.class);
                            startActivity(intent);
                            break;
                        case 10:
                            if (client.isUserLoggedIn()) {
                                client.logout();
                            }
                            break;
                        default:
                            Log.e("NavigationViewError", "select non-existing item");
                            //case 9:
                            //fragment = LearnMoreFragment.newInstance();
                    }

                    gotoFragment(fragment);

                    return true;
                }
            })
            .build();
    }

    private AccountHeader createAccountHeader() {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.kids_1)
            .withSelectionListEnabledForSingleProfile(false)
            .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                @Override
                public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                    // TODO Add go to profile fragment
                    return true;
                }
            })
            .addProfiles(
                new ProfileDrawerItem().withName("Lubna Dani").withEmail("lubna@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile))
            )
            .build();


        return headerResult;
    }

    private void gotoFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragment)
                .addToBackStack(null)
                .commit();
        }

        drawer.closeDrawer();
    }

    public void checkIfLoggedInAndProceed(Fragment fragment) {

        if (!ParseClient.newInstance(this).isUserLoggedIn()) {
            Log.d("DEBUG", "User is not logged in so go to Login");
            gotoLogin();
        } else {
            Log.d("DEBUG", "User is logged in so go to fragment");
            gotoFragment(fragment);
        }
    }


    public void gotoLogin() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    // TODO call profileActivity which should be used for changing all the user parameters
    public void gotoProfile() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }


}
