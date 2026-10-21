@REM Maven Wrapper Script for Windows
@REM Auto-downloads Maven if not present

@echo off
setlocal

set MAVEN_VERSION=3.9.8
set MAVEN_HOME=%USERPROFILE%\.m2\wrapper\apache-maven-%MAVEN_VERSION%
set MVN_CMD=%MAVEN_HOME%\bin\mvn.cmd

if not exist "%MVN_CMD%" (
    echo Downloading Apache Maven %MAVEN_VERSION%...
    set MAVEN_ZIP=%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip

    powershell -Command "Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip'"

    if not exist "%USERPROFILE%\.m2\wrapper" mkdir "%USERPROFILE%\.m2\wrapper"

    powershell -Command "Expand-Archive -Path '%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip' -DestinationPath '%USERPROFILE%\.m2\wrapper' -Force"

    echo Maven %MAVEN_VERSION% ready.
)

"%MVN_CMD%" %*
endlocal
