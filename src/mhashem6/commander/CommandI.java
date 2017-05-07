package mhashem6.commander;

public interface CommandI {

	String[] executableCommand();

	String[] options();

	String[] args();

	String toString();

}
