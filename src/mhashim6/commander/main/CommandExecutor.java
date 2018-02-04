package mhashim6.commander.main;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mhashim6.commander.exceptions.UnrecognisedCmdException;

/**
 * @author mhashim6
 */
public class CommandExecutor {

    private final static ProcessBuilder pb = new ProcessBuilder();
    private final static ExecutorService WORKERS = Executors.newCachedThreadPool();

    protected final static String NEW_LINE = System.getProperty("line.separator");
    //	private final static String		HEADER		= "--powered by commander library." + NEW_LINE + NEW_LINE;
    // ============================================================

    public static ProcessMonitor execute(String cmdLine) throws UnrecognisedCmdException {
        return execute(CommandBuilder.buildRawCommand(cmdLine), null, ExecutionOutputPrinter.DEFAULT_OUTPUT_PRINTER);
    }

    public static ProcessMonitor execute(Command cmd, File directory, ExecutionOutputPrinter outputPrinter)
            throws UnrecognisedCmdException {

        Process p = executeCommand(cmd, directory);
        recordOutput(p, outputPrinter);

        Future<ExecutionReport> futureReport = WORKERS.submit(new ExecutionCallable(p, cmd));
        return new ProcessMonitor(p, futureReport);
    }
    // ============================================================

    private static Process executeCommand(Command cmd, File directory) throws UnrecognisedCmdException {
        synchronized (pb) {
            try {
                pb.directory(directory);
                pb.command(cmd.executable());
                Process process = pb.start();
                return process;
            } catch (IOException e) {
                throw new UnrecognisedCmdException(cmd.string());
            }
        }
    }
    // ============================================================

    private static void recordOutput(Process p, ExecutionOutputPrinter outputPrinter) {
        //	outputPrinter.getAppender().appendStdText(HEADER);
        WORKERS.execute(() -> outputPrinter.handleStdStream(p.getInputStream()));
        WORKERS.execute(() -> outputPrinter.handleErrStream(p.getErrorStream()));
    }
    // ============================================================

    private final static class ExecutionCallable implements Callable<ExecutionReport> {

        private final Process p;
        private final Command cmd;

        public ExecutionCallable(Process p, Command cmd) {
            this.p = p;
            this.cmd = cmd;
        }

        @Override
        public ExecutionReport call() throws Exception {
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                //nothing.
            }
            return ExecutionReport.makeReport(cmd, p.exitValue());
        }
    }

}
