package com.teksystems.tekathon.teamup.ui.activity;

import android.os.Bundle;
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

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "HomeActivity";

    private Fragment fragment;

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

}