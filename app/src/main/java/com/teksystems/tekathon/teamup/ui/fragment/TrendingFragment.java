package com.teksystems.tekathon.teamup.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teksystems.tekathon.teamup.R;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class TrendingFragment extends Fragment {

    private static final String TAG = "TrendingFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_trending, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {

    }
}
