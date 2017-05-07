package mhashem6.commander;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandBuilder {
	protected ArrayList<String> commandLine;
	protected ArrayList<String> commandArgs;
	protected ArrayList<String> commandOptions;
	protected ArrayList<String> finalCommand;
	// ============================================================

	public CommandBuilder() {
		init();
	}
	// ============================================================

	public CommandBuilder(String cmdLine) {
		init();
		forCommandLine(cmdLine);
	}
	// ============================================================

	private void init() {
		commandLine = new ArrayList<>();
		commandArgs = new ArrayList<>();
		commandOptions = new ArrayList<>();
		finalCommand = new ArrayList<>();
	}
	// ============================================================

	/**
	 * will clear any previously set data if any
	 */
	public CommandBuilder forCommandLine(String line) {
		clearAll();
		if (line != null)
			commandLine.add(line);
		return this;
	}
	// ============================================================

	/**
	 * will clear any previous options if any
	 */
	public CommandBuilder withOptions(String[] params) {
		commandOptions.clear();
		if (params != null && params.length != 0)
			commandOptions.addAll(Arrays.asList(params));
		return this;
	}
	// ============================================================

	/**
	 * will clear any previous args if any
	 */
	public CommandBuilder withArgs(String[] args) {
		commandArgs.clear();
		if (args != null && args.length != 0)
			commandArgs.addAll(Arrays.asList(args));
		return this;
	}

	public CommandI build() {
		String executableCmdLine = finalCmdList().toString().replace(",", "");
		String[] executableCmd = executableCmdLine.substring(1, executableCmdLine.length() - 1).split(" ");

		return new CommandI() {
			@Override
			public String[] executableCommand() {
				return executableCmd;
			}

			@Override
			public String toString() {
				return executableCmdLine;
			}

			@Override
			public String[] options() {
				return commandOptions.toArray(new String[commandOptions.size()]);
			}

			@Override
			public String[] args() {
				return commandArgs.toArray(new String[commandArgs.size()]);
			}

		};

	}
	// ============================================================

	private ArrayList<String> finalCmdList() {
		finalCommand.clear();
		finalCommand.addAll(commandLine);
		finalCommand.addAll(commandOptions);
		finalCommand.addAll(commandArgs);

		return finalCommand;
	}
	// ============================================================

	private void clearAll() {
		commandLine.clear();
		commandOptions.clear();
		commandArgs.clear();
		finalCommand.clear();
	}
	// ============================================================

}
