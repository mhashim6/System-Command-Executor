package mhashim6.commander.main;

/**
 * @author mhashim6
 */
public interface ExecutionReport {
    int NO_EXIT_VALUE = 777;

    static ExecutionReport makeReport(Command cmd, int exitValue) {

        return new ExecutionReport() {

            @Override
            public int exitValue() {
                return exitValue;
            }

            @Override
            public Command command() {
                return cmd;
            }
        };
    }

    Command command();

    int exitValue();

}
