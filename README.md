Simple Library to make the process of executing system commands through java a simple task.
The library is thread-safe, can be used to execute multiple commands asynchronously.

------
**How to**

If you want to execute a command, and redirect the output to the `console`:

    try {
    CommandExecutor.execute("ping google.com");
    }
    catch (IOException e) {
    e.printStackTrace();
    }

------
However, this library gives you more control over the commands being executed; 

 - You can use the `CommandBuilder` as *wrapper* for the parts that form the command line you
   want to execute; such as arguments
   (`CommandBuilder#withArgs`) and
   options(`CommandBuilder#withOptions`).
   
 - You can *redirect* the `standard` and `error` outputs to any object You want, just *implement* the `Appender` interface.

 - You can *retrieve* the `exit code` of the process, or *abort* the process,
   by simply using the `ProcessMonitor` and `ExecutionReport` objects.


------
An advanced example:

    Command cmd = new CommandBuilder().forCommandLine("ping").withArgs("google.com").build();
    ExecutionOutputPrinter eop = new ExecutionOutputPrinter(new Appender() {
    
    @Override
    public void appendStdText(String text) {
    // your code to show std output lines.
    }
    
    @Override
    public void appendErrText(String text) {
    // your code to show error lines.
    }
    });
    
    try {
    ProcessMonitor pMonitor = CommandExecutor.execute(cmd, null, eop); //execute the command, redirect the output to eop.
    ExecutionReport report = pMonitor.getExecutionReport(); //blocks until the process finishes or gets aborted.
    
    String commandLine = cmd.string();
    int exitCode = report.exitValue();
    
    System.out.printf("command line: %s\nexecution finished with exit code: %d\n\n", commandLine, exitCode);
    }
    catch (UnrecognisedCmdException e) {
    System.err.println(e);
    }

to abort a running process (command), you can use the `ProcessMonitor` instance that you obtained previously:

    pMonitor.abort();


----------
