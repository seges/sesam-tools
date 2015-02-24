@echo off

rem 1st argument - absolute (!!!) path to JNDI bindings directory
rem 2nd argument - path to initializer script file

set BINDINGS_DIR=%1
set SCRIPT=%2

"%JAVA_HOME%/bin/java.exe" -Djava.naming.factory.initial=com.sun.jndi.fscontext.RefFSContextFactory -Djava.naming.provider.url=file://%BINDINGS_DIR% -jar %~dp0lib/sesam-tools-${pom.version}.jar -f %SCRIPT%

if %ERRORLEVEL% EQU 9009 goto ERROR_NO_JAVA
goto END

:ERROR_NO_JAVA
echo "Please set up JAVA_HOME variable"

:END
