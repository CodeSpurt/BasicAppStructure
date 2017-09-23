package com.codespurt.basicappstructureboilerplate.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.engine.callbacks.PermissionCallback;
import com.codespurt.basicappstructureboilerplate.views.BaseActivity;
import com.codespurt.basicappstructureboilerplate.views.custom.CustomTextView;
import com.codespurt.basicappstructureboilerplate.views.fragments.HistoryFragment;
import com.codespurt.basicappstructureboilerplate.views.fragments.HomeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Dashboard extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.coordinatorLayout_dashboard)
    CoordinatorLayout activity_layout;

    // setup BaseFragment with NavigationDrawer
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawerLayout_dashboard)
    DrawerLayout drawer;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    // index to identify current nav menu item
    public static int navItemIndex = 0;
    private View navHeader;

    // tags used to attach the fragments
    private static String TAG_HOME;
    private static String TAG_HISTORY;
    private static String TAG_LOGOUT;
    public static String CURRENT_TAG = TAG_HOME;

    // if false, set disableBackPress() to false
    // if true, set disableBackPress() to true
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void subOnCreate() {
        setToolbar(R.string.dashboard);
        ButterKnife.bind(this);

        // set tags and titles
        TAG_HOME = getResources().getString(R.string.home);
        TAG_HISTORY = getResources().getString(R.string.history);
        TAG_LOGOUT = getResources().getString(R.string.logout);

        setupNavigationView();

        // load home fragment
        CURRENT_TAG = TAG_HOME;
        loadFragment();
    }

    @Override
    protected CoordinatorLayout getLayout() {
        return activity_layout;
    }

    @Override
    protected void setFontsToViews() {

    }

    @Override
    protected boolean hasFragments() {
        return true;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    @Override
    protected boolean showToolbarBackArrow() {
        return false;
    }

    @Override
    protected boolean disableBackPress() {
        return true;
    }

    @Override
    protected void customBackPress() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                currentFragmentCount = navItemIndex;
                loadFragment();
                return;
            }
        }
    }

    @Override
    protected boolean showOptionsMenu() {
        return false;
    }

    @Override
    protected boolean isPermissionsRequired() {
        return false;
    }

    @Override
    protected int getPermissionCode() {
        return 0;
    }

    @Override
    protected PermissionCallback getPermissionCallback() {
        return null;
    }

    @Override
    protected void enableClicks() {

    }

    @Override
    protected void disableClicks() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    private void setupNavigationView() {
        setSupportActionBar(toolbar);
        mHandler = new Handler();

        // drawer header
        navHeader = navigationView.getHeaderView(0);
        CustomTextView header = (CustomTextView) navHeader.findViewById(R.id.nav_tv_header);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.navigation_history:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_HISTORY;
                        break;
                    case R.id.navigation_logout:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_LOGOUT;
                        break;
                    default:
                        navItemIndex = 0;
                }

                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                item.setChecked(true);
                loadFragment();
                return true;
            }
        });

        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void loadFragment() {
        if (CURRENT_TAG.equals(TAG_LOGOUT)) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        } else {
            selectNavMenu();

            if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
                drawer.closeDrawers();
                return;
            }

            getSupportActionBar().setTitle(CURRENT_TAG);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Fragment fragment = getFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    transaction.replace(R.id.container, fragment, CURRENT_TAG);
                    transaction.commit();
                }
            };

            if (runnable != null) {
                mHandler.post(runnable);
            }
            drawer.closeDrawers();
            invalidateOptionsMenu();
        }
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private Fragment getFragment() {
        currentFragmentCount = navItemIndex;
        switch (navItemIndex) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
            default:
                return new HomeFragment();
        }
    }
}