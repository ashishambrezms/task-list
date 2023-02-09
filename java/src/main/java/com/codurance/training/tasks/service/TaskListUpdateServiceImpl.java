package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskListUpdateServiceImpl implements TaskListUpdateService {

    private final Map<String, List<Task>> tasks;
    private final BufferedReader in;
    private final PrintWriter out;

    public TaskListUpdateServiceImpl(Map<String, List<Task>> tasks, BufferedReader in, PrintWriter out) {
        this.tasks = tasks;
        this.in = in;
        this.out = out;
    }

    @Override
    public void check(String idString) {
        setDone(idString, true);
    }

    @Override
    public void uncheck(String idString) {
        setDone(idString, false);
    }

    @Override
    public void setDone(String idString, Boolean done) {
        //int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId().equals(idString)) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", idString);
        out.println();
    }

    @Override
    public void setDeadline(String commandLine) throws ParseException {

        String[] subcommandRest = commandLine.split(" ", 2);
        String idString = subcommandRest[0];
        String deadline = subcommandRest[1];

        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId().equals(idString)) {
                    Date newDate = new SimpleDateFormat("dd/MM/yyyy").parse(deadline);
                    task.setDeadline(newDate);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", idString);
        out.println();
    }

    @Override
    public void delete(String idString) {
        for(Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for(Task task : project.getValue()){
                if(task.getId().equals(idString)){
                    project.getValue().remove(task);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", idString);
        out.println();
    }
}
