package com.teksystems.tekathon.teamup.ui.databinding.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.teksystems.tekathon.teamup.BR;
import com.teksystems.tekathon.teamup.callbacks.ReplyCallback;
import com.teksystems.tekathon.teamup.model.Reply;
import com.teksystems.tekathon.teamup.model.Topic;
import com.teksystems.tekathon.teamup.ui.activity.HomeActivity;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class ReplyViewModel extends BaseObservable {

    private static final String TAG = "ReplyViewModel";
    private Reply reply;
    private boolean isSelected;
    private boolean isNotReply;
    private Context context;
    private int recyclerPosition;
    private String answer;
    private ReplyCallback replyCallback;

    public ReplyViewModel(Reply reply, Context context, int recyclerPosition) {
        this.reply = reply;
        this.context = context;
        this.recyclerPosition = recyclerPosition;

        isNotReply = ((reply.getAnswer() == null) && (reply.getReplyId() == 0));

        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            Fragment visibleFragment = homeActivity.getVisibleFragment();
            if (visibleFragment instanceof ReplyCallback) {
                replyCallback = (ReplyCallback) visibleFragment;
            }
        }
    }

    @Bindable
    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
        notifyPropertyChanged(BR.reply);
    }

    public void postReply(View view) {
        Topic topic = this.reply.getTopic();

        Reply reply = new Reply();
        reply.setTopic(topic);
        reply.setAnswer(answer);

//        topic.getReplies().add(reply);
        if (replyCallback != null) {
            replyCallback.postReply(topic, reply);
        } else {
            Log.w(TAG, "selectReply: Callback not configured");
        }
    }

    public void selectReply(View view) {

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

    @Bindable
    public boolean isNotReply() {
        return isNotReply;
    }

    public void setNotReply(boolean notReply) {
        isNotReply = notReply;
        notifyPropertyChanged(BR.notReply);
    }

    @Bindable
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        notifyPropertyChanged(BR.answer);
    }

}