package com.teksystems.tekathon.teamup.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teksystems.tekathon.teamup.R;
import com.teksystems.tekathon.teamup.callbacks.ReplyCallback;
import com.teksystems.tekathon.teamup.model.Reply;
import com.teksystems.tekathon.teamup.model.Topic;
import com.teksystems.tekathon.teamup.recyclerview.adapter.TopicAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class TrendingFragment extends Fragment implements ReplyCallback {

    private static final String TAG = "TrendingFragment";

    private RecyclerView topicRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_trending, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        topicRecyclerView = (RecyclerView) rootView.findViewById(R.id.topicRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topicRecyclerView.setLayoutManager(linearLayoutManager);

        TopicAdapter topicAdapter = new TopicAdapter(getContext(), getDummyTopics());
        topicRecyclerView.setAdapter(topicAdapter);
    }

    @Override
    public void postReply(Topic topic, Reply reply) {
        TopicAdapter topicAdapter = (TopicAdapter) topicRecyclerView.getAdapter();
        topicAdapter.addReplyToTopic(topic, reply);
    }

    private List<Topic> getDummyTopics() {
        List<Topic> topics = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Topic topic = new Topic();
            topic.setTitle("Title " + (i + 1));
            topic.setDescription("Description " + (i + 1));
            topic.setTopicId(i);
            topic.setTopicDate(new Date());
            topic.setLastUpdatedDate(new Date());

            Random random = new Random();
            int noOfReplies = random.nextInt(10) + 5;
            for (int j = 0; j < noOfReplies; j++) {
                Reply reply = new Reply();
                reply.setTopic(topic);
                reply.setReplyId(j * 100 + i);
                reply.setAnswer("ANSWER " + (i + 1) + " - " + (j + 1));
                reply.setReplyBy("USER: " + (j + 1));
                topic.getReplies().add(reply);
            }
            topics.add(topic);
        }
        return topics;
    }
}
