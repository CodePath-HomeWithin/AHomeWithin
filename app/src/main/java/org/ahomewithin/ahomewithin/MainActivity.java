package org.ahomewithin.ahomewithin;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.ahomewithin.ahomewithin.home.HomeActivity;
import org.ahomewithin.ahomewithin.learnmore.LearnMoreActivity;
import org.ahomewithin.ahomewithin.services.ServicesActivity;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout dlDrawer;
    ActionBarDrawerToggle drawerToggle;
    NavigationView nvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dlDrawer = (DrawerLayout) findViewById(R.id.dlDrawer);
        drawerToggle = new ActionBarDrawerToggle(this, dlDrawer, toolbar, R.string.drawer_open,
                        R.string.drawer_close);
        dlDrawer.setDrawerListener(drawerToggle);

        nvView = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                dlDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        dlDrawer.closeDrawers();

        Class nextActivityClass = null;
        switch(menuItem.getItemId()) {
            case R.id.trainingAndTools:
                // nextActivityClass = TrainingAndTools.class;
                break;
            case R.id.services:
                nextActivityClass = ServicesActivity.class;
                break;
            case R.id.learnMore:
                nextActivityClass = LearnMoreActivity.class;
                break;
            case R.id.account:
                // nextActivityClass = UserActivity.class;
                break;
            case R.id.aboutUs:
                // fragment or activity?
                //fragmentClass = ThirdFragment.class;
                break;
            default:
                nextActivityClass = HomeActivity.class;
        }

        if (nextActivityClass != null) {
            Intent i = new Intent(this, nextActivityClass);
            startActivity(i);
        }
        dlDrawer.closeDrawers();
    }


    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
