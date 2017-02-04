package com.teksystems.tekathon.teamup.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mayank Tiwari on 04/02/17.
 */

public class Reply implements Serializable {

    private Topic topic;
    private long replyId;
    private String answer;
    private String replyBy;
    private Date replyTime;

    public Reply() {
    }

    public long getReplyId() {
        return replyId;
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(String replyBy) {
        this.replyBy = replyBy;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}