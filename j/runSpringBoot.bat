@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.6.7-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
cd /d C:\Users\10827937\OneDrive - LTIMindtree\Desktop\WorkSpaceProper\j\target\
set /p PORT="Enter the port number to run the application: "
java -jar j-0.0.1-SNAPSHOT.jar --server.port=%PORT%
pause