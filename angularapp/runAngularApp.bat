@echo off
set /p PORT="Enter the port number to run the Angular app: "
npm start -- --port %PORT%
pause


REM This batch file is used to run an Angular application.  

