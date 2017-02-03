package com.teksystems.tekathon.teamup.ui.fragment;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teksystems.tekathon.teamup.R;
import com.teksystems.tekathon.teamup.commons.MarshMallowPermission;
import com.teksystems.tekathon.teamup.model.Tag;
import com.teksystems.tekathon.teamup.recyclerview.adapter.TagAdapter;
import com.teksystems.tekathon.teamup.ui.activity.HomeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class NearMeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView tagRecyclerView;

    private MarshMallowPermission marshMallowPermission;
    private LatLngBounds.Builder mBounds = new LatLngBounds.Builder();

    private static final String TAG = "NearMeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_near_me, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        marshMallowPermission = new MarshMallowPermission(getContext(), this);
        tagRecyclerView = (RecyclerView) rootView.findViewById(R.id.tagRecyclerView);
        setupTagRecyclerView(tagRecyclerView);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setupTagRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        tagRecyclerView.setNestedScrollingEnabled(false);

        updateTags(loadDummyTags());
    }

    private List<Tag> loadDummyTags() {
        final List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add(new Tag("#TAG_" + (i + 1)));
        }
        return tags;
    }

    private void updateTags(List<Tag> tags) {
        TagAdapter tagAdapter = new TagAdapter(getActivity(), getContext(), tags);
        tagRecyclerView.setAdapter(tagAdapter);
    }

    private void getAddressByLatLong(LatLng latLong) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            final List<Address> addresses = geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1);
            if (addresses.size() > 0) {
                final Address address = addresses.get(0);
                final String locality = address.getLocality();
                final String subLocality = address.getSubLocality();
                final String postalCode = address.getPostalCode();

                final String addressData = String.format("Locality: %s, SubLocality: %s, PostalCode: %s\n\nLat-Long: %s", locality, subLocality, postalCode, latLong);
                Log.d(TAG, addressData);

                Toast.makeText(getContext(), addressData, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearMap() {
        if (mMap != null) {
            mMap.clear();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        mMap.setOnMarkerClickListener

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        boolean hasPermission = marshMallowPermission.isLocationPermissionGranted();
        if (!hasPermission) {
            try {
                marshMallowPermission.requestPermissionForLocation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            loadMap();
        }
    }


    private void loadMap() throws SecurityException {
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));
                    // we only want to grab the location once, to allow the user to pan and zoom freely.
                    mMap.setOnMyLocationChangeListener(null);
                }
            });

            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    callPlaceDetectionApi();
                    return true;
                }
            });
        }
    }

    private void callPlaceDetectionApi() throws SecurityException {
        Log.d(TAG, "callPlaceDetectionApi() called");

        final HomeActivity activity = (HomeActivity) getActivity();

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(activity.getGoogleApiClient(), null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {
                Log.d(TAG, "onResult() called with: likelyPlaces = [" + likelyPlaces + "]");
//                [PlaceLikelihoodBuffer{status=Status{statusCode=NETWORK_ERROR, resolution=null}, attributions=null}]
//                [PlaceLikelihoodBuffer{status=Status{statusCode=ERROR, resolution=null}, attributions=null}]
                PlaceLikelihood bestPlaceLikelihood = null;

                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    if (placeLikelihood.getLikelihood() > 0) {
                        if (bestPlaceLikelihood == null) {
                            bestPlaceLikelihood = placeLikelihood;
                        } else {
                            if (placeLikelihood.getLikelihood() > bestPlaceLikelihood.getLikelihood()) {
                                bestPlaceLikelihood = placeLikelihood;
                            }
                        }
                    }
                    Log.i(TAG, String.format("Place '%s' with " + "likelihood: %g", placeLikelihood.getPlace().getName(), placeLikelihood.getLikelihood()));
                }

                if (bestPlaceLikelihood != null) {

                    Log.i(TAG, String.format("Place '%s' has best likelihood: %g", bestPlaceLikelihood.getPlace().getName(), bestPlaceLikelihood.getLikelihood()));
                    final LatLng latLng = bestPlaceLikelihood.getPlace().getLatLng();
                    getAddressByLatLong(latLng);
                    //Animate Camera
                    if (mMap != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }
                } else {
                    Toast.makeText(getContext(), "We're unable to determine your location, Please check whether you have enabled Location Settings or not.", Toast.LENGTH_SHORT).show();
                }

                likelyPlaces.release();
            }
        });
    }

    private void addPointToViewPort(LatLng newPoint) {
        addPointToViewPort(newPoint, null);
    }

    private void addPointToViewPort(LatLng newPoint, Object object) {
        if (mMap != null) {
            mBounds.include(newPoint);

            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(newPoint);

//            if (object != null) {
//            }

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mBounds.build(), 50));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MarshMallowPermission.LOCATION_PERMISSION_REQUEST_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    loadMap();
//                    callPlaceDetectionApi();
                }
                break;
        }
    }
}