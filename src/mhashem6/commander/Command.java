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

package mhashem6.commander;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used to generate the command line that can be executed using
 * Executer classes,and can be specific to a single client, (e.g adb, fastboot
 * etc...), so if the client is set to adb for instance, all commands will be
 * executed with the argument adb at first.
 * 
 * @author mhashem6 (Muhammad Hashim)
 * 
 * @see SpecificCommand
 * @see SilentExecuter
 * 
 */
public class Command implements Serializable {

	private static final long serialVersionUID = 5227636747090849318L;

	protected String client;

	protected ArrayList<String> command;
	protected ArrayList<String> commandArgs;
	protected ArrayList<String> commandParams;
	protected ArrayList<String> finalCommand;
	// ============================================================

	/**
	 * to initialize the object of the class.
	 */
	protected void init() {
		command = new ArrayList<>();
		commandArgs = new ArrayList<>();
		commandParams = new ArrayList<>();
		finalCommand = new ArrayList<>();

	}

	// ============================================================
	// ============================================================

	// CONSTRUCTORS >>>>
	/**
	 * 
	 * Creates a blank Command object
	 */
	public Command() {
		init();

	}

	/**
	 * Creates a Command object from the passed String, set client to null if no
	 * client is required.
	 * 
	 * @param client
	 * @param cmd
	 */
	public Command(String client, String cmd) {
		this();

		setClient(client);
		setCommand(cmd);

	}
	// ============================================================

	/**
	 * Creates a Command object from a String[], set client to null if no client
	 * is required.
	 * 
	 * @param client
	 * @param cmd
	 */
	public Command(String client, String[] cmd) {
		this();

		setClient(client);
		setCommand(cmd);

	}
	// ============================================================

	/**
	 * Creates a Command object from a ArrayList<String>, set client to null if
	 * no client is required.
	 * 
	 * @param client
	 * @param cmd
	 */
	public Command(String client, ArrayList<String> cmd) {
		this();

		setClient(client);
		setCommand(cmd);
	}
	// ============================================================

	/**
	 * A copy Constructor
	 * 
	 * @param commandObject
	 */
	@SuppressWarnings("unchecked")
	public Command(Command commandObject) {
		this();
		client = commandObject.getClient();
		command = (ArrayList<String>) command.clone();
		commandArgs = (ArrayList<String>) commandArgs.clone();
		finalCommand = (ArrayList<String>) finalCommand.clone();
	}
	// ============================================================
	// ============================================================

	// CLIENT >>>>
	/**
	 * sets the client as the passed String Object
	 * 
	 * @param client
	 */
	public void setClient(String client) {

		this.client = client;

	}
	// ============================================================

	/**
	 * 
	 * @return the client String object
	 */
	public String getClient() {

		return (client != null) ? client : "";
	}
	// ============================================================

	/**
	 * sets the passed String object as the command.
	 * 
	 * @param cmd
	 * @see Commander#setCommand(String[] cmd)
	 * @see Commander#setCommand(ArrayList<String> cmd)
	 */
	public void setCommand(String cmd) {

		setCommand(cmd.split(" "));
	}
	// ============================================================

	/**
	 * sets the passed String[] as the command.
	 * 
	 * @param cmd
	 * @see Commander#setCommand(String cmd)
	 * @see Commander#setCommand(ArrayList<String> cmd)
	 */
	public void setCommand(String[] cmd) {

		clear();
		for (String c : cmd)
			if (c.startsWith("-")) 
				appendArgument(c);
			 else
				command.add(c);
	}
	// ============================================================

	/**
	 * sets the passed ArrayList<String> object as the command.
	 * 
	 * @param cmd
	 * @see Commander#setCommand(String cmd)
	 * @see Commander#setCommand(String[] cmd)
	 */
	public void setCommand(ArrayList<String> cmd) {

		setCommand((String[]) cmd.toArray());
	}
	// ============================================================
	// ============================================================

	// ARGUMENTS >>>>

	/**
	 * appends a new argument to the command.
	 * 
	 * @param argument
	 * @see Command#clearArguments()
	 */
	public boolean appendArgument(String argument) {
		return commandArgs.add(argument);

	}
	// ============================================================

	/**
	 * clears only the arguments
	 * 
	 * @see Command#appendArgument(String arg)
	 * @see Command#clear()
	 * @see Command#clearAll()
	 */
	public void clearArguments() {
		commandArgs.clear();

	}
	// ============================================================
	// ============================================================
	
	// PARAMETERS >>>>

	/**
	 * appends a new argument to the command.
	 * 
	 * @param parameter
	 * @see Command#clearParams()
	 */
	public boolean appendParameter(String parameter) {
		return commandParams.add(parameter);

	}
	// ============================================================

	/**
	 * clears only the parameters
	 * 
	 * @see Command#appendParameter(String arg)
	 * @see Command#clear()
	 * @see Command#clearAll()
	 */
	public void clearParameters() {
		commandParams.clear();

	}
	// ============================================================
	// ============================================================
	
	// FINAL COMMAND >>>>

	/**
	 * 
	 * @return the final version of the command line as String[]
	 * 
	 * @see Command#toString()
	 * @see Command#toList()
	 */
	public String[] toStringArray() {

		return toList().toArray(new String[finalCommand.size()]);
	}
	// ============================================================

	/**
	 * 
	 * @return the final version of the command line as ArrayList<String>
	 * 
	 * @see Command#toString()
	 * @see Command#toStringArray()
	 */
	public ArrayList<String> toList() {
		finalCommand.clear();
		if (getClient() != "")
			finalCommand.add(getClient());
		finalCommand.addAll(command);
		finalCommand.addAll(commandArgs);
		finalCommand.addAll(commandParams);

		return finalCommand;
	}
	// ============================================================

	/**
	 * 
	 * @return the command line as String
	 * 
	 * @see Command#toStringArray()
	 * @see Command#toList()
	 */
	@Override
	public String toString() {

		return toList().toString().replace(",", "");

	}
	// ============================================================
	// ============================================================

	// CLEAR COMMAND >>>>

	/**
	 * clears the command line, and the arguments, not the client
	 * 
	 * @see Command#clearArguments()
	 * @see Command#clearAll()
	 */
	public void clear() {
		command.clear();
		clearArguments();
		clearParameters();
		finalCommand.clear();

	}
	// ============================================================

	/**
	 * clears the client, the command, and the arguments.
	 * 
	 * @see Command#clear
	 */
	public void clearAll() {
		setClient(null);
		clear();

	}
	// ============================================================
	// ============================================================

		@Override
	public boolean equals(Object otherObject) {
		if (otherObject instanceof Command)
			if (this.toString().equals(otherObject.toString()))
				return true;
		return false;
	}
}
