package com.codurance.training.tasks.service;

import java.text.ParseException;

public interface TaskListViewService {
    void show(String viewCriteria);
    void listTasksDueToday() throws ParseException;
}
