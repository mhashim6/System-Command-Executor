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
* Source : https://github.com/mhashim6/Commander
*
*/

package mhashem6.commander.exceptions;

import java.io.IOException;

/**
 * shall be thrown when the output of the command is declared as Error by the
 * client.
 * 
 * @author mhashem6 (Mohamed Hashem)
 *
 */
@SuppressWarnings("serial")
public class OutputIsErrorException extends IOException {

	private String client;

	/**
	 * the public constructor that need the clent's name (e.g adb)
	 * 
	 * @param client
	 */
	public OutputIsErrorException(String client) {
		this.client = client;
	}

	public String toString() {
		return client + " reported an error";

	}

}
