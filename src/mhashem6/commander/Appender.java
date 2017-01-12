package mhashem6.commander;

/**
 * The class that implements this interface will be able to display the output
 * of line by line, by implementing appendLine method
 * 
 * @see ExecuterAndWriter
 * 
 * @author mhashim6 (Muhammad Hashim)
 *
 */
public interface Appender {

	/**
	 * appends a new line of output
	 * 
	 * @param outputLine
	 */
	void appendText(String text);

	void appendErrText(String text);
}
// ============================================================
