package com.teksystems.tekathon.teamup.recyclerview.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teksystems.tekathon.teamup.R;
import com.teksystems.tekathon.teamup.databinding.TopicListItemBinding;
import com.teksystems.tekathon.teamup.model.Reply;
import com.teksystems.tekathon.teamup.model.Topic;
import com.teksystems.tekathon.teamup.ui.databinding.viewmodel.TopicViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayank Tiwari on 24/09/16.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    private List<Topic> topicList;
    private Context context;
    private boolean isOpen;
    private long selectedId;

    public TopicAdapter(Context context, List<Topic> topicList) {
        this.context = context;
        this.topicList = topicList;
    }

    public List<Topic> getTopicList() {
        if (topicList == null) {
            topicList = new ArrayList<>();
        }
        return topicList;
    }

    public void addReplyToTopic(Topic topic, Reply reply) {
        int i = 0;
        for (Topic refTopic : getTopicList()) {
            if (refTopic.getTopicId() == topic.getTopicId()) {
                refTopic.getReplies().add(reply);
                isOpen = true;
                selectedId = refTopic.getTopicId();
                notifyItemChanged(i);
                if (context != null) {
                    Toast.makeText(context, "Thoughts posted successfully!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            i++;
        }
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Topic topic = topicList.get(position);
        TopicViewModel topicViewModel = new TopicViewModel(topic, context, position);
        if (isOpen && (selectedId == topic.getTopicId())) {
            topicViewModel.setSelected(true);
            isOpen = false;
            selectedId = 0;
        }
        holder.bindTo(topicViewModel);
    }

    @Override
    public int getItemCount() {
        return getTopicList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TopicListItemBinding binding;
        private RecyclerView repliesRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            repliesRecyclerView = (RecyclerView) binding.getRoot().findViewById(R.id.repliesRecyclerView);
        }

        public RecyclerView getRepliesRecyclerView() {
            return repliesRecyclerView;
        }

        public void setRepliesRecyclerView(RecyclerView repliesRecyclerView) {
            this.repliesRecyclerView = repliesRecyclerView;
        }

        public TopicListItemBinding getBinding() {
            return binding;
        }

        public void bindTo(TopicViewModel topicViewModel) {
            binding.setTopic(topicViewModel);
            binding.addOnRebindCallback(new OnRebindCallback() {
                @Override
                public boolean onPreBind(ViewDataBinding binding) {
                    ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
                    TransitionManager.beginDelayedTransition(sceneRoot);
                    return true;
                }
            });
            binding.executePendingBindings();
        }
    }
}