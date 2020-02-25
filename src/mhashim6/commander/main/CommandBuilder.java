package mhashim6.commander.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mhashim6
 */
public class CommandBuilder {

    private String commandLine;
    private final ArrayList<String> cmdArgs, cmdOptions, finalCommand;

    //	private static final String	WHITE_SPACE		= " ";
    private static final String EMPTY_STRING = "";
    private static final Pattern QUOTES_PATTERN = Pattern.compile("([^\"|^']\\S*|[\"|'].+?[\"|'])\\s*");
    // ============================================================

    public CommandBuilder() {
        commandLine = EMPTY_STRING;
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
        this.commandLine = line;
        return this;
    }
    // ============================================================

    public CommandBuilder addOption(String option) {
        if (option != null) cmdOptions.add(option);

        return this;
    }

    /**
     * side effect: will clear any previously set options if any.
     */
    public CommandBuilder withOptions(String... params) {
        cmdOptions.clear();
        if (params != null && params.length != 0) cmdOptions.addAll(Arrays.asList(params));
        return this;
    }
    // ============================================================

    public CommandBuilder addArg(String arg) {
        if (arg != null) cmdArgs.add(arg);

        return this;
    }

    /**
     * side effect: will clear any previously set arguments if any.
     */
    public CommandBuilder withArgs(String... args) {
        cmdArgs.clear();
        if (args != null && args.length != 0) cmdArgs.addAll(Arrays.asList(args));
        return this;
    }
    // ============================================================

    public Command build() {

        String executableCmdLine = finalCmdLine(finalCmdList());
        executableCmdLine = executableCmdLine.substring(0, executableCmdLine.length() - 1);
        String[] executableCmd = splitCmd(executableCmdLine);

        return new CommandImpl(executableCmdLine, executableCmd);
    }

    private ArrayList<String> finalCmdList() {
        finalCommand.clear();
        finalCommand.add(commandLine);
        finalCommand.addAll(cmdOptions);
        finalCommand.addAll(cmdArgs);

        return finalCommand;
    }

    private String finalCmdLine(ArrayList<String> cmdList){
        final StringBuilder cmd = new StringBuilder();
        for (String segment : cmdList) {
            cmd.append(segment);
            cmd.append(' ');
        }
        return cmd.toString();
    }
    // ============================================================

    private static String[] splitCmd(String cmd) {
        List<String> strings = new ArrayList<>();
        Matcher m = QUOTES_PATTERN.matcher(cmd);
        while (m.find()) {
            String token = m.group(1);
            token = token.startsWith("'") || token.startsWith("\"") ?
                    token.replace("'", EMPTY_STRING).replace("\"", EMPTY_STRING) : token;
            strings.add(token);
        }
        return strings.toArray(new String[0]);
    }

    private void clearAll() {
        commandLine = EMPTY_STRING;
        cmdOptions.clear();
        cmdArgs.clear();
        finalCommand.clear();
    }
    // ============================================================

    public static final Command buildRawCommand(String cmdLine) {
        return new CommandImpl(cmdLine, splitCmd(cmdLine));
    }

    private static class CommandImpl implements Command {

        private final String cmdLine;
        private final String[] executableCmd;

        private CommandImpl(String cmdLine, String[] executableCmd) {
            this.cmdLine = cmdLine;
            this.executableCmd = executableCmd;
        }

        @Override
        public String[] executable() {
            return executableCmd;
        }

        @Override
        public String string() {
            return cmdLine;
        }

        @Override
        public String toString() {
            return string();
        }
    }
}
