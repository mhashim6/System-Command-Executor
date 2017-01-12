package mhashem6.commander;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandBuilder {

	private ArrayList<String> finalCommand;

	public CommandBuilder() {
		init();
	}

	public CommandBuilder(String cmdLine) {
		init();
		forCommandLine(cmdLine);
	}

	private void init() {
		finalCommand = new ArrayList<>();

	}

	/**
	 * will clear any previous data if any
	 */
	public CommandBuilder forCommandLine(String line) {
		finalCommand.clear();
		if (line != null)
			finalCommand.add(line);
		return this;
	}

	public CommandBuilder withOptions(String[] params) {
		if (params != null && params.length != 0)
			finalCommand.addAll(Arrays.asList(params));
		return this;
	}

	public CommandBuilder withArgs(String[] args) {
		if (args != null && args.length != 0)
			finalCommand.addAll(Arrays.asList(args));
		return this;
	}

	public CommandI build() {

		String executableCmdLine = finalCommand.toString().replaceAll(",", "");
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

		};

	}

}
