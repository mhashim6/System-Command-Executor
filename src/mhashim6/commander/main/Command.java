package mhashim6.commander.main;

/**
 * @author mhashim6
 */
public interface Command {

	String[] executableCommand();

	String[] options();

	String[] args();

	String toString();

}
