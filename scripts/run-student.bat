ECHO OFF

:: Checking if JAVA_HOME is set
if "%JAVA_HOME%" == "" (
    echo.
    echo JAVA_HOME environment variable is not set. Please follow the readme.md to set it up.
    echo.
    exit /b 1
)

cd ..

SET ECOURSE_ST=ecourse.app.student.console\target\app.student.console-0.1.0.jar;ecourse.app.student.console\target\dependency\*;

java -cp %ECOURSE_ST% ECourseStudentApp
