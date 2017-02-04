package com.teksystems.tekathon.teamup.callbacks;

import com.teksystems.tekathon.teamup.model.Reply;
import com.teksystems.tekathon.teamup.model.Topic;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public interface ReplyCallback {

    public void postReply(Topic topic, Reply reply);

}