# Commander
A simple Library made to make the process of executing command lines through java programs simpler. It also returns the output line by line.  
This library is the base of (Simple-ADB) program found here :
[xda-developers](http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155) , [Source-Forge](https://sourceforge.net/projects/sadb/) , [GitHub](https://github.com/mhashim6/Simple-ADB)

```java
Command cmd = new Command();
cmd.setClient("adb");
cmd.setCommand("devices -l");
//optional : cmd.setCommand("devices", "-l"):
ExecuterStreamFormatter streamFormatter = new ExecuterStreamFormatter(); //redirecting output of execution to the console,
//optional: implement the Appender interface to redirect the standard and error outputs your way.

Executer executer = new Executer(streamFormatter);
try{
executer.execute(cmd);
}catch(IOException e){
e.printStackTrace();
}
```
see the source code for detailed usage information.  
-development is in hiatus-
