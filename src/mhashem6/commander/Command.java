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

public interface Command {

	String[] executableCommand();

	String[] options();

	String[] args();

	String toString();

}
