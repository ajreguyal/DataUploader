@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  DataUploader startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and DATA_UPLOADER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\DataUploader-1.0.1.jar;%APP_HOME%\lib\spring-aop-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-aspects-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-beans-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-context-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-context-support-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-core-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-expression-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-instrument-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-instrument-tomcat-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-jdbc-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-jms-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-messaging-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-orm-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-oxm-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-test-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-tx-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-web-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-webmvc-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-webmvc-portlet-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-websocket-4.3.2.RELEASE.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\junit-4.12.jar;%APP_HOME%\lib\jsoup-1.9.2.jar;%APP_HOME%\lib\jackson-core-2.8.1.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.21.jar;%APP_HOME%\lib\mysql-connector-java-6.0.3.jar;%APP_HOME%\lib\hamcrest-all-1.3.jar;%APP_HOME%\lib\aspectjweaver-1.8.9.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\jdom-1.1.jar;%APP_HOME%\lib\slf4j-api-1.7.21.jar;%APP_HOME%\lib\log4j-1.2.17.jar

@rem Execute DataUploader
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %DATA_UPLOADER_OPTS%  -classpath "%CLASSPATH%" com.ajreguyal.main.java.DataUploader %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable DATA_UPLOADER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%DATA_UPLOADER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
