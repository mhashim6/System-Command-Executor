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

package mhashem6.commander.exceptions;

import java.io.IOException;

@SuppressWarnings("serial")
/**
 * shall be thrown when trying to execute a command, but the System cannot
 * recognize it.
 * 
 * @author mhashem6 (Mohamed Hashem)
 *
 */
public class UnrecognisedCmdException extends IOException {
	private String cmd;

	/**
	 * the public constructors that requiers the command that caused the error.
	 * 
	 * @param cmd
	 */
	public UnrecognisedCmdException(String cmd) {
		this.cmd = cmd;
	}

	public String toString() {
		return "could not recognise " + cmd;

	}

}
