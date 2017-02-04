package com.teksystems.tekathon.teamup.ui.databinding.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.teksystems.tekathon.teamup.BR;
import com.teksystems.tekathon.teamup.callbacks.ItemSelectedCallback;
import com.teksystems.tekathon.teamup.model.Tag;
import com.teksystems.tekathon.teamup.ui.activity.HomeActivity;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class TagViewModel extends BaseObservable {

    private static final String TAG = "TagViewModel";
    private Tag tag;
    private boolean isSelected;
    private Context context;
    private int recyclerPosition;
    private ItemSelectedCallback itemSelectedCallback;

    public TagViewModel(Tag tag, Context context, int recyclerPosition) {
        this.tag = tag;
        this.context = context;
        this.recyclerPosition = recyclerPosition;

        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            Fragment visibleFragment = homeActivity.getVisibleFragment();
            if (visibleFragment instanceof ItemSelectedCallback) {
                itemSelectedCallback = (ItemSelectedCallback) visibleFragment;
            }
        }
    }

    @Bindable
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        notifyPropertyChanged(BR.tag);
    }

    public void selectTag(View view) {
        if (itemSelectedCallback != null) {
            setSelected(!isSelected);
//            setSelected(!isSelected);
            itemSelectedCallback.onItemSelected(isSelected, tag, recyclerPosition);
        } else {
            Log.w(TAG, "selectTag: Callback not configured");
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getRecyclerPosition() {
        return recyclerPosition;
    }

    public void setRecyclerPosition(int recyclerPosition) {
        this.recyclerPosition = recyclerPosition;
    }

    @Bindable
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        notifyPropertyChanged(BR.selected);
    }
}