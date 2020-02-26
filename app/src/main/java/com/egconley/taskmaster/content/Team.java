package com.egconley.taskmaster.content;

import java.util.LinkedList;

public class Team {

    String name;
    LinkedList<Task> teamListOfTasks;

    public Team(String name) {
        this.name = name;
        this.teamListOfTasks = new LinkedList<>();
    }
}
