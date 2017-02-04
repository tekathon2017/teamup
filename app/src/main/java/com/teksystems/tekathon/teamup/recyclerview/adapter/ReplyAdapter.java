package com.teksystems.tekathon.teamup.recyclerview.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teksystems.tekathon.teamup.R;
import com.teksystems.tekathon.teamup.databinding.ReplyListItemBinding;
import com.teksystems.tekathon.teamup.model.Reply;
import com.teksystems.tekathon.teamup.model.Topic;
import com.teksystems.tekathon.teamup.ui.databinding.viewmodel.ReplyViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayank Tiwari on 24/09/16.
 */

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    private Topic topic;
    private List<Reply> replyList;
    private Context context;

    public ReplyAdapter(Context context, List<Reply> replyList, Topic topic) {
        this.context = context;
        this.topic = topic;
        this.replyList = replyList;
    }

    public List<Reply> getReplyList() {
        if (replyList == null) {
            replyList = new ArrayList<>();
        }
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Reply reply;
        if (position < (getItemCount() - 1)) {
            reply = replyList.get(position);
        } else {
            reply = new Reply();
            reply.setTopic(topic);
        }
        ReplyViewModel replyViewModel = new ReplyViewModel(reply, context, position);
        holder.bindTo(replyViewModel);
    }

    @Override
    public int getItemCount() {
        return getReplyList().size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ReplyListItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public ReplyListItemBinding getBinding() {
            return binding;
        }

        public void bindTo(ReplyViewModel replyViewModel) {
            binding.setReply(replyViewModel);
            binding.executePendingBindings();
        }
    }
}