package mhashem6.commander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecuterStreamFormatter {

	protected BufferedReader bufferedReader;

	protected Appender outputObject;

	/**
	 * initialize a new object with a specified Appender instance to show
	 * output.
	 * 
	 * @param outputObject
	 * @see ExecuterStreamFormatter#ExecuterStreamFormatter()
	 */
	public ExecuterStreamFormatter(Appender outputObject) {
		setAppender(outputObject);
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
			public void appendStdLine(String stdOutputLine) {
				System.out.println(stdOutputLine);
			}

			@Override
			public void appendErrLine(String errOutputLine) {
				System.err.println(errOutputLine);

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
	 * @param StdInputStream
	 * @throws IOException
	 */
	public void FormatStdStream(InputStream StdInputStream) throws IOException {
		formatStream(StdInputStream, false);

	}

	// ============================================================

	/**
	 ** Formats the passed error InputStream, shows the output using the Appender
	 * instance.
	 * 
	 * @param errorStream
	 * @throws IOException
	 */
	public void FormatErrStream(InputStream errorStream) throws IOException {

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
	protected void formatStream(InputStream inputStream, boolean isError) throws IOException {

		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String tempLine = null;

		// Read output
		while ((tempLine = bufferedReader.readLine()) != null) {
			showOutputLine(tempLine, isError);
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
			outputObject.appendErrLine(line + System.getProperty("line.separator"));
		else
			outputObject.appendStdLine(line + System.getProperty("line.separator"));

	}
	// ============================================================

	/**
	 * closes the used resources objects used to obtain the output
	 * 
	 * @throws IOException
	 */
	void closeResources() throws IOException {

		if (bufferedReader != null) {
			bufferedReader.close();
			bufferedReader = null;
		}

		System.gc();

	}
	// ============================================================

}
