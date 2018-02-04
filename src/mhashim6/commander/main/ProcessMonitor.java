package mhashim6.commander.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author mhashim6
 */
public final class ProcessMonitor {

    private final Process process;
    private final Future<ExecutionReport> futureReport;

    public ProcessMonitor(Process runningProcess, Future<ExecutionReport> futureReport) {
        this.process = runningProcess;
        this.futureReport = futureReport;
    }

    public boolean isAlive() {
        return (process == null) ? false : process.isAlive();
    }

    public int abort() {
        if (isAlive()) {
            process.destroyForcibly();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                //do nothing.
            }
        }
        return process.exitValue();
    }

    public ExecutionReport getExecutionReport() {
        ExecutionReport report = null;
        try {
            report = futureReport.get();
        } catch (InterruptedException | ExecutionException e) {
            //do nothing.
        }
        return report;
    }
}