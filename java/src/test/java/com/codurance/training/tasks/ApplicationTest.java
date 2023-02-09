package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class ApplicationTest {
    public static final String PROMPT = "> ";
    private final PipedOutputStream inStream = new PipedOutputStream();
    private final PrintWriter inWriter = new PrintWriter(inStream, true);

    private final PipedInputStream outStream = new PipedInputStream();
    private final BufferedReader outReader = new BufferedReader(new InputStreamReader(outStream));

    private Thread applicationThread;

    public ApplicationTest() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new PipedInputStream(inStream)));
        PrintWriter out = new PrintWriter(new PipedOutputStream(outStream), true);
        TaskList taskList = new TaskList(in, out);
        applicationThread = new Thread(taskList);
    }

    @Before public void
    start_the_application() {
        applicationThread.start();
    }

    @After public void
    kill_the_application() throws IOException, InterruptedException {
        if (!stillRunning()) {
            return;
        }

        Thread.sleep(1000);
        if (!stillRunning()) {
            return;
        }

        applicationThread.interrupt();
        throw new IllegalStateException("The application is still running.");
    }

    @Test(timeout = 1000) public void
    it_works() throws IOException {
        execute("show");

        execute("add project secrets");
        execute("add task secrets ID1 Eat more donuts.");
        execute("add task secrets ID2 Destroy all humans.");

        execute("show");
        readLines(
            "secrets",
            "    [ ] ID1: Eat more donuts.",
            "    [ ] ID2: Destroy all humans.",
            ""
        );

        execute("add project training");
        execute("add task training ID3 Four Elements of Simple Design");
        execute("add task training ID4 SOLID");
        execute("add task training ID5 Coupling and Cohesion");
        execute("add task training ID6 Primitive Obsession");
        execute("add task training ID7 Outside-In TDD");
        execute("add task training ID8 Interaction-Driven Design");

        execute("check ID1");
        execute("check ID3");
        execute("check ID5");
        execute("check ID6");

        execute("show");
        readLines(
                "secrets",
                "    [x] ID1: Eat more donuts.",
                "    [ ] ID2: Destroy all humans.",
                "",
                "training",
                "    [x] ID3: Four Elements of Simple Design",
                "    [ ] ID4: SOLID",
                "    [x] ID5: Coupling and Cohesion",
                "    [x] ID6: Primitive Obsession",
                "    [ ] ID7: Outside-In TDD",
                "    [ ] ID8: Interaction-Driven Design",
                ""
        );

        execute("quit");
    }

    private void execute(String command) throws IOException {
        read(PROMPT);
        write(command);
    }

    private void read(String expectedOutput) throws IOException {
        int length = expectedOutput.length();
        char[] buffer = new char[length];
        outReader.read(buffer, 0, length);
        assertThat(String.valueOf(buffer), is(expectedOutput));
    }

    private void readLines(String... expectedOutput) throws IOException {
        for (String line : expectedOutput) {
            read(line + lineSeparator());
        }
    }

    private void write(String input) {
        inWriter.println(input);
    }

    private boolean stillRunning() {
        return applicationThread != null && applicationThread.isAlive();
    }
}
