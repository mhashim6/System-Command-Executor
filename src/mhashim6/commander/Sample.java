package mhashim6.commander;

import java.io.IOException;

import mhashim6.commander.exceptions.UnrecognisedCmdException;
import mhashim6.commander.main.Appender;
import mhashim6.commander.main.Command;
import mhashim6.commander.main.CommandBuilder;
import mhashim6.commander.main.CommandExecutor;
import mhashim6.commander.main.ExecutionOutputPrinter;
import mhashim6.commander.main.ProcessMonitor;

/**
 * @author mhashim6
 */
public class Sample {

	public static void main(String[] args) {

		//compact form, output will show using System.out.
		try {
			CommandExecutor.execute(new CommandBuilder().forCommandLine("adb devices").withOptions("-l").build());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		//advanced form, where you can redirect output to any object you want.
		//just pass a custom ExecutionOutputPrinter with Appender implementation.

		Command cmd = new CommandBuilder("adb shell netstat").build();
		ExecutionOutputPrinter eop = new ExecutionOutputPrinter(new Appender() {

			@Override
			public void appendStdText(String text) {
				// handle lines here.
			}

			@Override
			public void appendErrText(String text) {
				// handle error lines here.
			}
		});

		try {
			ProcessMonitor pMonitor = CommandExecutor.execute(cmd, eop);

			while (pMonitor.isAlive());

			pMonitor.getExecutionReport();
		}
		catch (UnrecognisedCmdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
