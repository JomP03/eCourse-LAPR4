ECHO OFF

:: Checking if JAVA_HOME is set
if "%JAVA_HOME%" == "" (
    echo.
    echo JAVA_HOME environment variable is not set. Please follow the readme.md to set it up.
    echo.
    exit /b 1
)

cd ..

mvn clean install -pl ecourse.app.board.server -am -DskipTests