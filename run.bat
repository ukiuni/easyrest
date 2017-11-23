:loop
  set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_152"
  .\gradrew.bat --no-daemon clean && .\gradrew.bat --no-daemon test
  set JAVA_HOME="C:\Program Files\Zulu\zulu-8"
  .\gradrew.bat --no-daemon clean && .\gradrew.bat --no-daemon test
goto loop
