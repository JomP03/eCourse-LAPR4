ECHO OFF

:: Checking if JAVA_HOME is set
if "%JAVA_HOME%" == "" (
    echo.
    echo JAVA_HOME environment variable is not set. Please follow the readme.md to set it up.
    echo.
    exit /b 1
)

cd ..

SET ECOURSE_BC=ecourse.app.board.client\target\app.board.client-0.1.0.jar;ecourse.app.board.client\target\dependency\*;

java -cp %ECOURSE_BC% ECourseBoardClientApp
