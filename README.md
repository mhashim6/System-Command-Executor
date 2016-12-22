# Commander

This library is the base of (Simple-ADB) program found here :
[xda-developers](http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155) , [Source-Forge](https://sourceforge.net/projects/sadb/) , [GitHub](https://github.com/mhashim6/Simple-ADB)

```java
Command cmd = new Command();
cmd.setClient("adb");
cmd.setCommand("devices -l");
//optional : cmd.setCommand("devices", "-l"):
Executer executer = new Executer(line -> System.out.println(line)); //redirecting output of execution.
try{
executer.execute(cmd);
}catch(IOException e){
e.printStackTrace();
}
```
see the source code for detailed usage information.
