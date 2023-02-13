package com.codurance.training.tasks.utils;

import java.io.PrintWriter;
import java.util.regex.Pattern;

public class TaskListUtils {

    public static void help(PrintWriter out) {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task id> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  today");
        out.println("  delete <task id>");
        out.println();
    }

    public static boolean isValidId(String idString){
        return Pattern.matches("\\w+", idString);
    }
}
