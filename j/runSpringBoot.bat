@echo off
cd /d "C:\Users\10827937\OneDrive - LTIMindtree\Desktop\WorkSpaceProper\j"
set /p PORT="Enter the port number to run the application: "
java -jar target\j-0.0.1-SNAPSHOT.jar --server.port=%PORT%
pause