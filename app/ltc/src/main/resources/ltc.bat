@echo off
setlocal enabledelayedexpansion

set "PROPERTIES_FILE=%~dp0../config/ext.properties"

set "JAVA_PROPS="

if exist "%PROPERTIES_FILE%" (
    for /f "usebackq tokens=1,* delims==" %%A in ("%PROPERTIES_FILE%") do (
        if not "%%A"=="" if not "%%A:~0,1#"=="#" (
            set "JAVA_PROPS=!JAVA_PROPS! -D%%A=%%B"
        )
    )
)

java %JAVA_PROPS% -jar "%~dp0../libs/ltc.jar" %*

endlocal
