A simple Library made to make the process of executing command lines through java programs simpler. It also returns the output line by line.  

```java
new Executer(new ExecuterStreamFormatter())  
.execute(new CommandBuilder()  
.forCommandLine("adb devices")  
.withOptions(new String[]{"-l"})  
.build());  
//this will execute "adb devices -l" command.
}catch(IOException e){
e.printStackTrace();
}
```
see the source code for detailed usage information.  

This library is the base of (Simple-ADB) program found here :
[xda-developers](http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155) , [Source-Forge](https://sourceforge.net/projects/sadb/) , [GitHub](https://github.com/mhashim6/Simple-ADB)..
