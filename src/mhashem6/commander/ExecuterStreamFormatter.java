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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mhashem6.commander.Appender;

public class ExecuterStreamFormatter {

	protected BufferedReader bufferedReader;

	protected Appender outputObject;

	/**
	 * initialize a new object with a specified Appender instance to show
	 * output.
	 * 
	 * @param appender
	 * @see ExecuterStreamFormatter#ExecuterStreamFormatter()
	 */
	public ExecuterStreamFormatter(Appender appender) {
		setAppender(appender);
	}
	// ============================================================

	/**
	 * will pass the output directly to the console.
	 * 
	 * @param outputObject
	 * @see ExecuterStreamFormatter#ExecuterStreamFormatter(Appender
	 *      outputObject)
	 */
	public ExecuterStreamFormatter() {
		this(new Appender() {

			@Override
			public void appendText(String text) {
				System.out.println(text);
			}

			@Override
			public void appendErrText(String text) {
				System.err.println(text);
			}
		});
	}
	// ============================================================

	/**
	 * 
	 * @return the Appender instance of this object.
	 */
	public Appender getAppender() {
		return outputObject;
	}

	// ============================================================

	/**
	 * changes the Appender instance of this object.
	 * 
	 * @param outputObject
	 */
	public void setAppender(Appender outputObject) {
		this.outputObject = outputObject;
	}
	// ============================================================

	/**
	 * Formats the passed standard InputStream, shows the output using the
	 * Appender instance.
	 * 
	 * @param stdInputStream
	 * @throws IOException
	 */
	public void FormatStdStream(InputStream stdInputStream) {
		formatStream(stdInputStream, false);
	}

	// ============================================================

	/**
	 ** Formats the passed error InputStream, shows the output using the
	 * Appender instance.
	 * 
	 * @param errorStream
	 * @throws IOException
	 */
	public void FormatErrStream(InputStream errorStream) {
		formatStream(errorStream, true);
	}
	// ============================================================

	/**
	 * deals with the InputStream object line by line
	 * 
	 * @param inputStream
	 * @param isError
	 * @throws IOException
	 */
	protected void formatStream(InputStream inputStream, boolean isError) {
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String tempLine = null;
		//		StringBuilder built = new StringBuilder();

		// Read output
		try {
			//			int count = 1;
			while ((tempLine = bufferedReader.readLine()) != null) {
				//				built.append(tempLine).append(System.getProperty("line.separator"));
				//				if (count++ == 4) {
				showOutputLine(tempLine, isError);
				//					count = 0;
				//					built = new StringBuilder();
				//				}
			}
		}
		catch (IOException e) {// just stop
		}
	}
	// ============================================================

	/**
	 * displays the output line by line
	 * 
	 * @param line
	 */
	protected void showOutputLine(String line, boolean isError) {

		if (isError)
			outputObject.appendErrText(line);
		else
			outputObject.appendText(line);

	}
	// ============================================================

	/**
	 * closes the used resources objects used to obtain the output
	 * 
	 * @throws IOException
	 */
	void closeResources() {

		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			}
			catch (IOException e) {
			}
			bufferedReader = null;
		}

		System.gc();

	}
	// ============================================================

}
