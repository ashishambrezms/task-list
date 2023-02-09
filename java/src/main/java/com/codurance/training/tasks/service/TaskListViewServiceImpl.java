package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class TaskListViewServiceImpl implements TaskListViewService {

    private final Map<String, List<Task>> tasks;
    private final BufferedReader in;
    private final PrintWriter out;

    public TaskListViewServiceImpl(Map<String, List<Task>> tasks, BufferedReader in, PrintWriter out) {
        this.tasks = tasks;
        this.in = in;
        this.out = out;
    }

    @Override
    public void show(String viewCriteria) {



        switch (viewCriteria){
            case "project":
                displayMap(tasks);
                break;
            case "deadline":
                Map<String, List<Task>> mapByDeadline = tasks.entrySet().stream()
                        .flatMap(project -> project.getValue().stream())
                        .collect(groupingBy(task -> new SimpleDateFormat("dd/MM/yyyy").format(task.getDeadline())));
                displayMap(mapByDeadline);
                break;
            case "date":
                Map<String, List<Task>> mapByDate = tasks.entrySet().stream()
                        .flatMap(project -> project.getValue().stream())
                        .collect(groupingBy(task -> new SimpleDateFormat("dd/MM/yyyy").format(task.getCreatedOn())));
                displayMap(mapByDate);
                break;
        }
    }

    @Override
    public void listTasksDueToday() throws ParseException {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (new SimpleDateFormat("dd/MM/yyyy").format(task.getDeadline())
                        .equals((new SimpleDateFormat("dd/MM/yyyy")).format(new Date()))){
                    out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
                }
            }
        }
    }

    private void displayMap(Map<String, List<Task>> tasks){
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }
}
