package com.codurance.training.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Task {
    private final String id;
    private final String description;
    private Boolean done;
    private Date createdOn;
    private Date deadline;

    public Task(String id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.createdOn = new Date();
        this.deadline = new Date();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }
}
