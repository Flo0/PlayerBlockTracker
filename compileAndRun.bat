@echo off

SET serverLocation=C:\Users\suoto\Desktop\DESK\1.16Spigot

call mvn clean install

set "artifactId="
for /f "tokens=3 delims=<>" %%a in ('find /i "<artifactId>" ^< "pom.xml"') do (
  set "artifactId=%%a"
  If not [%artifactId%]==[""] (
    goto :end1
  )
)
:end1


set "version="
for /f "tokens=3 delims=<>" %%a in ('find /i "<version>" ^< "pom.xml"') do (
  set "version=%%a"
  If not [%version%]==[""] (
    goto :end2
  )
)
:end2

SET jarName=%artifactId%-%version%.jar
SET jarPath=target\%jarName%

SET serverPluginLoc=%serverLocation%\plugins

xcopy %jarPath% %serverPluginLoc% /Y

SET serverStartScript=%serverLocation%\start.bat

cd %serverLocation%

%serverStartScript%