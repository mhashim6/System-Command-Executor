/**
* Commander Library is a simple Library made to make the process of executing
* command lines through java programs simpler.
* Copyright (C) 2016 mhashem6 > (Muhammad Hashim)
* 
* This program is free software: you can redistribute it and/or modify it under
* the terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
* 
* You should have received a copy of the GNU General Public License along with
* this program. If not, see <http://www.gnu.org/licenses/>.
* 
* this library is the base of Simple-ADB program : http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155
* you can contact me @ abohashem.com@gmail.com
* Source : 
*
*/

package mhashem6.commander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mhashem6.commander.exceptions.*;

/**
 * The class the can execute/abort a command, with ability of
 * producing(automatically) and writing (not automatically) an output.
 * 
 * @see Command
 * @see SpecificCommand
 * @see ExecuterAndWriter
 * @author mhashem6 (Muhammad Hashim)
 */
public class Executer {

	protected Command cmd;

	protected ProcessBuilder pb;
	protected Process process;

	protected InputStreamReader stdReader;
	protected InputStreamReader errReader;

	protected BufferedReader stdOutputBr;
	protected BufferedReader errOutputBr;

	protected String outputStdStr;
	protected String outputErrStr;
	protected boolean hasError;

	protected StringBuilder outputToSave;

	private Appender appendrObject;

	/**
	 * 
	 * The public constructor that requires a Appender implementing object to
	 * display the output, if null is passed, the output will be written in the
	 * console.
	 * 
	 * @param appendrObject
	 * 
	 * @see Appender
	 */
	public Executer(Appender appendrObject) {
		clearOutput();
		this.appendrObject = appendrObject;
	}
	// ============================================================

	/**
	 * a method that executes the command line generated from Command object.
	 * 
	 * @param command
	 * @throws IOException
	 */
	public void execute(Command command) throws IOException {

		clearOutput();
		executeSilently(command);

		recordOutput();

	}
	// ============================================================

	/**
	 * a method that executes the command line generated from Command object,
	 * doesn't produce output.
	 * 
	 * @param command
	 * @throws UnrecognisedCmdException
	 */
	protected void executeSilently(Command cmd) throws UnrecognisedCmdException {
		this.cmd = cmd;

		try {
			pb = new ProcessBuilder(cmd.toStringArray());
			process = pb.start();
		} catch (IOException e) {

			throw new UnrecognisedCmdException(cmd.toString());

		}
	}
	// ============================================================

	/**
	 * the method responsible for recording the output of the command line
	 * 
	 * @throws IOException
	 * 
	 */
	protected void recordOutput() throws IOException {

		try {

			/** Get input streams */
			stdReader = new InputStreamReader(process.getInputStream());
			errReader = new InputStreamReader(process.getErrorStream());

			stdOutputBr = new BufferedReader(stdReader);
			errOutputBr = new BufferedReader(errReader);

			outputToSave = new StringBuilder();
			/** Read command standard output */
			while ((outputStdStr = stdOutputBr.readLine()) != null) {

				store(outputStdStr);
				outputStdStr = null;
			}

			/** Read command error output */
			while ((outputErrStr = errOutputBr.readLine()) != null) {
				store(outputErrStr);
				hasError = true;
				outputErrStr = null;
			}

		}

		finally {

			if (hasError == true)
				throw new OutputIsErrorException(cmd.getClient() != "" ? cmd.getClient() : "System");
			closeResources();

		}

	}
	// ============================================================

	/**
	 * to store the output line by line.
	 * 
	 * @param line
	 */
	protected void store(String line) {

		outputToSave.append((line + System.getProperty("line.separator")));
		showOutputLine(line);

		line = null;
	}
	// ============================================================

	/**
	 * displays the output line by line
	 * 
	 * @param line
	 */
	protected void showOutputLine(String line) {

		appendrObject.appendLine(line + System.getProperty("line.separator"));

		line = null;

	}
	// ============================================================

	/**
	 * a method that aborts the current command process
	 * 
	 * @throws CmdAbortedException
	 * @throws IOException
	 * 
	 * @see Executer#isAlive()
	 */
	public void abort() throws IOException {
		if (isAlive()) {
			// closeResources();
			process.destroyForcibly();

		}
	}
	// ============================================================

	/**
	 * 
	 * @return true if the command execution is still running
	 */
	public boolean isAlive() {

		if (process != null)
			return (process.isAlive());

		return false;
	}
	// ============================================================

	/**
	 * returns a String representing the full output of the execution
	 * 
	 * @throws NoOutputException
	 * @throws ExecNotFinishedException
	 */
	public String getOutput() throws NoOutputException, ExecNotFinishedException {

		if (isAlive())
			throw new ExecNotFinishedException();

		if (outputToSave == null || outputToSave.equals(""))
			throw new NoOutputException();

		return outputToSave.toString();
	}
	// ============================================================

	/**
	 * @return the previously executed Command object stored in the Executer
	 */
	public Command getCommand() {
		if (cmd == null)
			throw new NullPointerException();
		return cmd;
	}

	/**
	 * clears the output of the execution
	 */
	public void clearOutput() {
		outputToSave = null;
		outputStdStr = null;
		outputErrStr = null;
		hasError = false;
	}
	// ============================================================

	/**
	 * closes the used resources objects used to obtain the output
	 * 
	 * @throws IOException
	 */
	protected void closeResources() throws IOException {

		cmd = null;
		pb = null;
		process = null;

		stdReader.close();
		stdOutputBr.close();
		stdReader = null;
		stdOutputBr = null;

		errReader.close();
		errOutputBr.close();
		errReader = null;
		errOutputBr = null;

	}
	// ============================================================

}
