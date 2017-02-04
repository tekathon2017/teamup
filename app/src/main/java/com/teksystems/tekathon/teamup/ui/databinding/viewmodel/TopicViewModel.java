package com.teksystems.tekathon.teamup.ui.databinding.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.teksystems.tekathon.teamup.BR;
import com.teksystems.tekathon.teamup.callbacks.ItemSelectedCallback;
import com.teksystems.tekathon.teamup.model.Topic;
import com.teksystems.tekathon.teamup.ui.activity.HomeActivity;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class TopicViewModel extends BaseObservable {

    private static final String TAG = "TopicViewModel";
    private Topic topic;
    private boolean isSelected;
    private Context context;
    private int recyclerPosition;
    private ItemSelectedCallback itemSelectedCallback;

    public TopicViewModel(Topic topic, Context context, int recyclerPosition) {
        this.topic = topic;
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
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
        notifyPropertyChanged(BR.topic);
    }

    public void selectTopic(View view) {
//        if (itemSelectedCallback != null) {
        setSelected(!isSelected);
//            setSelected(!isSelected);
//            itemSelectedCallback.onItemSelected(isSelected, topic, recyclerPosition);
//        } else {
//            Log.w(TAG, "selectTopic: Callback not configured");
//        }
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