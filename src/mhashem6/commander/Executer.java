/**
 * Commander Library is a simple Library made to make the process of executing
 * command lines through java programs simpler. Copyright (C) 2016 mhashem6 >
 * (Muhammad Hashim)
 * 
 * this library is the base of Simple-ADB program :
 * http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155
 * you can contact me @ abohashem.com@gmail.com Source :
 *
 */

package mhashem6.commander;

import java.io.IOException;

import mhashem6.commander.exceptions.*;

/**
 * @see Command
 * @author mhashim6 (Muhammad Hashim)
 */
public class Executer {

	protected ProcessBuilder pb;
	protected Process		 process;

	protected ExecuterStreamFormatter eStreamFormatter;

	public static final int	NO_EXIT_VALUE = 777;
	protected int			exitval		  = NO_EXIT_VALUE;

	/**
	 * 
	 * A constructor that requires a pre-initialized ExecuterStreamFormatter
	 * object to handle the output.
	 * 
	 * @param eStreamFormatter
	 * 
	 * @see ExecuterStreamFormatter
	 */
	public Executer(ExecuterStreamFormatter eStreamFormatter) {
		this.eStreamFormatter = eStreamFormatter;
	}
	// ============================================================

	/**
	 * executes the command line generated from Command object. it will block
	 * until the execution finishes, or it gets aborted.
	 * 
	 * @param command
	 * @throws IOException
	 */
	public synchronized void execute(Command cmd) throws UnrecognisedCmdException {
		exitval = NO_EXIT_VALUE;
		executeCommand(cmd);
		recordOutput();
		abort();
	}
	// ============================================================

	protected void executeCommand(Command command) throws UnrecognisedCmdException {

		try {
			pb = new ProcessBuilder(command.executableCommand());
			process = pb.start();
		}
		catch (IOException e) {
			throw new UnrecognisedCmdException(command.toString());
		}
	}
	// ============================================================

	protected void recordOutput() {
		eStreamFormatter.getAppender().appendText("--powered by commander library."
				+ System.getProperty("line.separator") + System.getProperty("line.separator"));
		eStreamFormatter.FormatStdStream(process.getInputStream());
		eStreamFormatter.FormatErrStream(process.getErrorStream());
	}
	// ============================================================

	/**
	 * aborts the current command process
	 * 
	 * @throws IOException
	 * 
	 * @see Executer#isAlive()
	 */
	public void abort() {
		if (isAlive()) {
			process.destroyForcibly();
			exitval = process.exitValue();
		}
		eStreamFormatter.closeResources();
	}
	// ============================================================

	/**
	 * 
	 * @return true if the command execution is still running
	 */
	public boolean isAlive() {
		return (process == null) ? false : process.isAlive();
	}
	// ============================================================

	/**
	 * 
	 * @return exit value of the process, or Executer.NO_EXIT_VALUE if the exit
	 *         value couldn't be retrieved successfully.
	 */
	public int getExitValue() {
		return exitval;
	}

}
