package org.ahomewithin.ahomewithin.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

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
import org.ahomewithin.ahomewithin.fragments.ChatFragment;
import org.ahomewithin.ahomewithin.fragments.ChatTabsFragment;
import org.ahomewithin.ahomewithin.fragments.DetailFragment;
import org.ahomewithin.ahomewithin.fragments.EventsFragment;
import org.ahomewithin.ahomewithin.fragments.HomeFragment;
import org.ahomewithin.ahomewithin.fragments.LearnMoreFragment;
import org.ahomewithin.ahomewithin.fragments.LoginFragment;
import org.ahomewithin.ahomewithin.fragments.MapFragment;
import org.ahomewithin.ahomewithin.fragments.ProfileFragment;
import org.ahomewithin.ahomewithin.fragments.StreamPagerFragment;
import org.ahomewithin.ahomewithin.models.Item;
import org.ahomewithin.ahomewithin.util.LoginCallback;
import org.ahomewithin.ahomewithin.util.MapMarkers;
import org.parceler.Parcels;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements LoginCallback {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nvView)
    NavigationView nvView;
    private Drawer drawer;
    private AccountHeader accountHeader;
    private ParseClient client;
    private boolean closeToExit;

    private static final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = ParseClient.newInstance(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

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
                .commit();

        createDrawer();

        if (!isOnline()) {
            showSnackbar("Oops!  Please check internet connection!");
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        String tag = StreamPagerFragment.FRAGMENT_TAG;
        gotoFragment(streamPagerFragment, tag);
    }

    public void onSelectNearYou(View v) {
        Fragment mapFragment = MapFragment.newInstance();
        String tag = MapFragment.FRAGMENT_TAG;
        gotoFragment(mapFragment, tag);
    }

    public void onSelectToolsAndTechniques(View v) {
        Fragment streamPagerFragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.STORE, false);
        String tag = StreamPagerFragment.FRAGMENT_TAG;
        gotoFragment(streamPagerFragment, tag);
    }

    private void createDrawer() {
        accountHeader = createAccountHeader();
        //create the drawer and remember the `Drawer` result object
        PrimaryDrawerItem homeItem = createDrawerIcon(R.string.home, R.drawable.ic_sidemenu_home);
        PrimaryDrawerItem mapItem = createDrawerIcon(R.string.near_you, R.drawable.ic_sidemenu_nearyou);
        PrimaryDrawerItem myLibraryItem = createDrawerIcon(R.string.library, R.drawable.ic_sidemenu_library);
        PrimaryDrawerItem trainingItem = createDrawerIcon(R.string.training_and_tools, R.drawable.ic_sidemenu_training);
        PrimaryDrawerItem eventsItem = createDrawerIcon(R.string.events, R.drawable.ic_sidemenu_events);
        PrimaryDrawerItem learnMoreItem = createDrawerIcon(R.string.learn_more, R.drawable.ic_sidemenu_more);
        PrimaryDrawerItem aboutUsItem = createDrawerIcon(R.string.about_us, R.drawable.ic_sidemenu_about);
        PrimaryDrawerItem settingsItem = new PrimaryDrawerItem().withName(R.string.action_settings).withIconTintingEnabled(true);
        PrimaryDrawerItem chatRoomItem = createDrawerIcon(R.string.chat, R.drawable.ic_sidemenu_chat);
        PrimaryDrawerItem profileItem = createDrawerIcon(R.string.logout, R.drawable.ic_sidemenu_logout);

        // do something with the clicked item :D
        //fragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.LIBRARY);
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        homeItem,
                        new DividerDrawerItem(),
                        mapItem,
                        myLibraryItem,
                        trainingItem,
                        eventsItem,
                        learnMoreItem,
                        new DividerDrawerItem(),
                        aboutUsItem,
                        //settingsItem,
                        chatRoomItem,
                        profileItem)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        Fragment fragment = null;
                        String tag = "";
                        Log.d("DEBUG", "Clicked item nº" + position);
                        switch (position) {
                            case 1:
                                fragment = HomeFragment.newInstance();
                                tag = HomeFragment.FRAGMENT_TAG;
                                break;
                            case 3:
                                fragment = MapFragment.newInstance();
                                tag = MapFragment.FRAGMENT_TAG;
                                break;
                            case 4:
                                fragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.LIBRARY, true);
                                tag = StreamPagerFragment.FRAGMENT_TAG;
                                break;
                            case 5:
                                fragment = StreamPagerFragment.newInstance(StreamPagerFragment.ViewType.STORE, false);
                                tag = StreamPagerFragment.FRAGMENT_TAG;
                                break;
                            case 6:
                                fragment = EventsFragment.newInstance();
                                tag = EventsFragment.FRAGMENT_TAG;
                                break;
                            case 7:
                                fragment = LearnMoreFragment.newInstance();
                                tag = LearnMoreFragment.FRAGMENT_TAG;
                                break;
                            case 9:
                                fragment = AboutUsFragment.newInstance();
                                tag = AboutUsFragment.FRAGMENT_TAG;
                                break;
                            case 10:
                                if (!client.isUserLoggedIn()) {
                                    fragment = LoginFragment.newInstance(REQUEST_CODE);
                                    tag = LoginFragment.FRAGMENT_TAG;
                                } else {
                                    fragment = ChatTabsFragment.newInstance();
                                    tag = ChatTabsFragment.FRAGMENT_TAG;
                                }
                                break;
                            case 11:
                                if (client.isUserLoggedIn()) {
                                    fragment = ProfileFragment.newInstance();
                                    tag = ProfileFragment.FRAGMENT_TAG;
                                }
                                break;
                            default:
                                Log.e("NavigationViewError", "select non-existing item");
                                //case 9:
                                //fragment = LearnMoreFragment.newInstance();
                        }
                        gotoFragment(fragment, tag);
                        return true;
                    }
                })
                .build();
    }

    private PrimaryDrawerItem createDrawerIcon(int name, int icon) {

        return new PrimaryDrawerItem()
                .withName(name)
                .withIcon(icon)
                .withIconColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_dark))
                .withSelectedIconColor(ContextCompat.getColor(getApplicationContext(), R.color.accent))
                .withIconTintingEnabled(true)
                .withTextColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_dark));
    }

    private AccountHeader createAccountHeader() {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.logo_drawer_background)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                .withCompactStyle(true)
                .withHeightDp(100)
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        // TODO Add go to profile fragment
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flContent, ProfileFragment.newInstance(), null)
                                .addToBackStack(null)
                                .commit();

                        drawer.closeDrawer();
                        return true;
                    }
                })
                //.addProfiles(createProfileDrawerItem())
                .build();


    return headerResult;
}

    private ProfileDrawerItem createProfileDrawerItem() {
        return new ProfileDrawerItem()
                .withName("Lubna Dani")
                .withEmail("lubna@gmail.com")
                .withIcon(getResources().getDrawable(R.drawable.profile));
    }

    private void gotoFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            // Clean all fragments when using drawer
            // TODO Not working
//            getSupportFragmentManager().popBackStack(
//                    HomeFragment.FRAGMENT_TAG, 0);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContent, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }

        drawer.closeDrawer();
    }

    private void clearBackstack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (!closeToExit) {
                Toast.makeText(this, "Click one more time to exit", Toast.LENGTH_SHORT).show();
                closeToExit = true;
            } else {
                finish();
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    // TODO call profileActivity which should be used for changing all the user parameters
    public void gotoProfile() {
        Intent intent = new Intent(this, ProfileFragment.class);
        startActivity(intent);
    }

    @Override
    public void onPostLogin(int requestCode, Parcelable... extra) {
        getSupportFragmentManager().popBackStackImmediate();
        switch (requestCode) {
            case REQUEST_CODE:
                ChatTabsFragment chatTabsFragment = ChatTabsFragment.newInstance();
                gotoFragment(chatTabsFragment, ChatTabsFragment.FRAGMENT_TAG);
                break;
            case MapMarkers.REQUEST_CODE:
                String otherEmail = MapMarkers.curUserOnMap.email;
                ChatFragment chatFragment = ChatFragment.newIntance(otherEmail);
                gotoFragment(chatFragment, ChatFragment.FRAGMENT_TAG);
                break;
            case DetailFragment.REQUEST_CODE:
                DetailFragment detailFragment = DetailFragment.newInstance(
                        (Item) Parcels.unwrap(extra[0]));
                gotoFragment(detailFragment, DetailFragment.FRAGMENT_TAG);
                break;
            default:
                Log.e("onPostLogin", String.format("the code %s is unknown", requestCode));
        }
    }
}
