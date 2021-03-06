package com.teksystems.tekathon.teamup.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.teksystems.tekathon.teamup.R;
import com.teksystems.tekathon.teamup.ui.fragment.NearMeFragment;
import com.teksystems.tekathon.teamup.ui.fragment.PublishFragment;
import com.teksystems.tekathon.teamup.ui.fragment.TrendingFragment;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "HomeActivity";

    private Fragment fragment;
    boolean doubleBackToExitPressedOnce;

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                    .build();
        }

        init();
    }

    private void init() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        loadFragmentId(item.getItemId());
                        return true;
                    }
                });
        loadFragmentId(R.id.action_near_me);
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    private void loadFragmentId(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String title = "";
        switch (id) {
            case R.id.action_near_me:
                Log.i(TAG, "onNavigationItemSelected: Near Me Selected");
//                fragment = new TrendingFragment();
                fragment = new NearMeFragment();
                title = "Near Me";
                break;
            case R.id.action_trending:
                Log.i(TAG, "onNavigationItemSelected: Trending Selected");
                fragment = new TrendingFragment();
                title = "Trending Now";
                break;
            case R.id.action_publish:
                Log.i(TAG, "onNavigationItemSelected: Publish Selected");
                fragment = new PublishFragment();
                title = "Publish";
                break;
//            case R.id.action_settings:
//                Log.i(TAG, "onNavigationItemSelected: Sessions Selected");
//                fragment = new SettingFragment();
//                title = "Settings";
//                break;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fragment, title);
        transaction.commit();

        updateTitle(title);
    }

    private void updateTitle(String title) {
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: " + connectionResult.getErrorCode());
        Toast.makeText(HomeActivity.this, "We're unable to determine your Location due to Google Places API failure.", Toast.LENGTH_LONG).show();
    }

    /**
     * Handled back pressed button lifecycle.
     */
    @Override
    public void onBackPressed() {
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            if (!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                try {
                    super.onBackPressed();
                    System.exit(0);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    super.onBackPressed();
                }
            }
        }
    }
}