package mhashim6.commander.main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author mhashim6
 */
public class CommandBuilder {

	private final ArrayList<String> cmdLine, cmdArgs, cmdOptions, finalCommand;

	private static final String	WHITE_SPACE		= " ";
	private static final String	COMMA					= ",";
	private static final String	EMPTY_STRING	= "";
	// ============================================================

	public CommandBuilder() {
		cmdLine = new ArrayList<>();
		cmdArgs = new ArrayList<>();
		cmdOptions = new ArrayList<>();
		finalCommand = new ArrayList<>();
	}
	// ============================================================

	public CommandBuilder(String cmdLine) {
		this();
		forCommandLine(cmdLine);
	}
	// ============================================================

	/**
	 * side effect: will clear any previously set data if any.
	 */
	public synchronized CommandBuilder forCommandLine(String line) {
		clearAll();
		cmdLine.add(line);
		return this;
	}
	// ============================================================

	/**
	 * side effect: will clear any previously set options if any.
	 */
	public synchronized CommandBuilder withOptions(String... params) {
		cmdOptions.clear();
		if (params != null && params.length != 0) cmdOptions.addAll(Arrays.asList(params));
		return this;
	}
	// ============================================================

	/**
	 * side effect: will clear any previously set arguments if any.
	 */
	public synchronized CommandBuilder withArgs(String... args) {
		cmdArgs.clear();
		if (args != null && args.length != 0) cmdArgs.addAll(Arrays.asList(args));
		return this;
	}

	public synchronized Command build() {
		String executableCmdLine = finalCmdList().toString().replace(COMMA, EMPTY_STRING);
		String[] executableCmd, options, args;

		executableCmd = executableCmdLine.substring(1, executableCmdLine.length() - 1).split(WHITE_SPACE);
		options = cmdOptions.toArray(new String[cmdOptions.size()]);
		args = cmdArgs.toArray(new String[cmdArgs.size()]);

		return new Command() {
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
				return options;
			}

			@Override
			public String[] args() {
				return args;
			}
		};
	}
	// ============================================================

	private ArrayList<String> finalCmdList() {
		finalCommand.clear();
		finalCommand.addAll(cmdLine);
		finalCommand.addAll(cmdOptions);
		finalCommand.addAll(cmdArgs);

		return finalCommand;
	}
	// ============================================================

	private void clearAll() {
		cmdLine.clear();
		cmdOptions.clear();
		cmdArgs.clear();
		finalCommand.clear();
	}
	// ============================================================

}
