package mhashim6.commander.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mhashim6.commander.main.Appender;

/**
 * @author mhashim6
 */
public class ExecutionOutputPrinter {

    /**
     * will pass the output directly to System.out .
     */
    public static final ExecutionOutputPrinter DEFAULT_OUTPUT_PRINTER = new ExecutionOutputPrinter(new Appender() {

        @Override
        public void appendStdText(String text) {
            System.out.println(text);
        }

        @Override
        public void appendErrText(String text) {
            System.err.println(text);
        }
    });

    protected Appender appender;

    public ExecutionOutputPrinter(Appender appender) {
        this.appender = appender;
    }
    // ============================================================

    public Appender getAppender() {
        return appender;
    }
    // ============================================================

    void handleStdStream(InputStream stdInputStream) {
        formatStream(stdInputStream, false);
    }

    void handleErrStream(InputStream errorStream) {
        formatStream(errorStream, true);
    }

    protected void formatStream(InputStream inputStream, boolean isError) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while ((line = br.readLine()) != null)
                showOutputLine(line, isError);
        } catch (IOException e) {
            showOutputLine(e.fillInStackTrace().toString() + CommandExecutor.NEW_LINE, true);
        }
    }
    // ============================================================

    private void showOutputLine(String line, boolean isError) {

        if (isError)
            appender.appendErrText(line);
        else
            appender.appendStdText(line);
    }
    // ============================================================

}
