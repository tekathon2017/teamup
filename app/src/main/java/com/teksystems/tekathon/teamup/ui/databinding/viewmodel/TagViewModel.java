package com.teksystems.tekathon.teamup.ui.databinding.viewmodel;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.teksystems.tekathon.teamup.BR;
import com.teksystems.tekathon.teamup.model.Tag;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class TagViewModel extends BaseObservable {

    private Tag tag;
    private Activity mActivity;
    private int recyclerPosition;

    public TagViewModel(Tag tag, Activity mActivity, int recyclerPosition) {
        this.tag = tag;
        this.mActivity = mActivity;
        this.recyclerPosition = recyclerPosition;
    }

    @Bindable
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        notifyPropertyChanged(BR.tag);
    }

    public Activity getmActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public int getRecyclerPosition() {
        return recyclerPosition;
    }

    public void setRecyclerPosition(int recyclerPosition) {
        this.recyclerPosition = recyclerPosition;
    }
}