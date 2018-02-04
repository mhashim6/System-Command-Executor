package mhashim6.commander.exceptions;

import java.io.IOException;

@SuppressWarnings("serial")
public class UnrecognisedCmdException extends IOException {
    private String cmd;

    /**
     * the public constructors that requires the command that caused the error.
     *
     * @param cmd
     */
    public UnrecognisedCmdException(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "could not recognise " + cmd;

    }

}
