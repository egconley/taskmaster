package com.egconley.taskmaster.content;

public class Task {

    int task_number;
    String title;
    private String body;
    private String state;

    public Task() {

    }

    Task(int task_number, String title, String body, String state) {
        this.task_number = task_number;
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public int getTask_number() {
        return task_number;
    }

    public void setTask_number(int task_number) {
        this.task_number = task_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
