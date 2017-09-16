package mhashim6.commander.main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author mhashim6
 */
public class CommandBuilder {

	private String									cmdLine;
	private final ArrayList<String>	cmdArgs, cmdOptions, finalCommand;

	private static final String	WHITE_SPACE		= " ";
	private static final String	COMMA					= ",";
	private static final String	EMPTY_STRING	= "";
	// ============================================================

	public CommandBuilder() {
		cmdLine = EMPTY_STRING;
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
	public CommandBuilder forCommandLine(String line) {
		clearAll();
		this.cmdLine = line;
		return this;
	}
	// ============================================================

	/**
	 * side effect: will clear any previously set options if any.
	 */
	public CommandBuilder withOptions(String... params) {
		cmdOptions.clear();
		if (params != null && params.length != 0) cmdOptions.addAll(Arrays.asList(params));
		return this;
	}
	// ============================================================

	/**
	 * side effect: will clear any previously set arguments if any.
	 */
	public CommandBuilder withArgs(String... args) {
		cmdArgs.clear();
		if (args != null && args.length != 0) cmdArgs.addAll(Arrays.asList(args));
		return this;
	}

	public Command build() {
		String executableCmdLine = finalCmdList().toString().replace(COMMA, EMPTY_STRING);
		String[] executableCmd = executableCmdLine.substring(1, executableCmdLine.length() - 1).split(WHITE_SPACE);

		return new Command() {
			@Override
			public String[] executable() {
				return executableCmd;
			}

			@Override
			public String string() {
				return executableCmdLine;
			}
		};
	}
	// ============================================================

	private ArrayList<String> finalCmdList() {
		finalCommand.clear();
		finalCommand.add(cmdLine);
		finalCommand.addAll(cmdOptions);
		finalCommand.addAll(cmdArgs);

		return finalCommand;
	}
	// ============================================================

	private void clearAll() {
		cmdLine = EMPTY_STRING;
		cmdOptions.clear();
		cmdArgs.clear();
		finalCommand.clear();
	}
	// ============================================================

	public static final Command buildRawCommand(String cmdLine) {
		return new Command() {

			@Override
			public String string() {
				return cmdLine;
			}

			@Override
			public String[] executable() {
				return cmdLine.split(" ");
			}

		};
	}
}
