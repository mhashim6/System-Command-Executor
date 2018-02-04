package mhashim6.commander;

import mhashim6.commander.exceptions.UnrecognisedCmdException;
import mhashim6.commander.main.Appender;
import mhashim6.commander.main.Command;
import mhashim6.commander.main.CommandBuilder;
import mhashim6.commander.main.CommandExecutor;
import mhashim6.commander.main.ExecutionOutputPrinter;
import mhashim6.commander.main.ExecutionReport;
import mhashim6.commander.main.ProcessMonitor;

/**
 * @author mhashim6
 */
public class Sample {

    public static void main(String[] args) {

        Command cmd = new CommandBuilder("ping").withArgs("google.com").build();
        ExecutionOutputPrinter eop = new ExecutionOutputPrinter(new Appender() {

            @Override
            public void appendStdText(String text) {
                System.out.println(text);
                //or your code to show std output lines.
            }

            @Override
            public void appendErrText(String text) {
                System.err.println(text);
                //or your code to show error lines.
            }
        });

        try {
            ProcessMonitor pMonitor = CommandExecutor.execute(cmd, null, eop); //execute the command, redirect the output to eop.
            ExecutionReport report = pMonitor.getExecutionReport(); //blocks until the process finishes or gets aborted.

            String commandLine = cmd.string();
            int exitCode = report.exitValue();

            System.out.printf("command line: %s\nexecution finished with exit code: %d\n\n", commandLine, exitCode);
        } catch (UnrecognisedCmdException e) {
            System.err.println(e);
        }
    }
}
