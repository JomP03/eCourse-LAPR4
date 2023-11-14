# Project eCourse

## Copyright

 <b> <span style="color: orange"> Copyright © 2023, GaMaVa Team </span> </b>

## Who do I talk to?
+ <span style="color: cornflowerblue">   1211061@isep.ipp.pt  </span> → Gustavo Jorge
+ <span style="color: cornflowerblue">  1211063@isep.ipp.pt </span> → João Leitão
+ <span style="color: cornflowerblue">  1211073@isep.ipp.pt </span> → Guilherme Sousa
+ <span style="color: cornflowerblue"> 1211076@isep.ipp.pt </span> → Pedro Monteiro
+ <span style="color: cornflowerblue"> 1211261@isep.ipp.pt </span> → João Fortuna


## 1. Description of the Project

Ecourse is an online learning management system that is designed to provide a platform for students, teachers, and managers to interact and manage course materials in an efficient and organized way.
<br>
+ Students can access the platform to view course materials, participate in online discussions, submit assignments, and take assessments. They can also track their progress, view grades, and receive feedback from their teachers.
  <br> </br>
+ Teachers can use the platform to create and manage courses, upload course materials, grade assignments, and communicate with students. They can also track student progress and performance.
  <br> </br>
+ Managers can oversee the entire system and manage the users, courses, and resources. They can monitor user activity and make adjustments to the system as needed.

The ecourse project is designed to make the learning process easier, more accessible, and more engaging for all users involved. It is a powerful tool for educational institutions, training centers, and other organizations looking to offer online courses and training programs.

## 2. Planning and Technical Documentation

# <span style="color: cornflowerblue">Windows</span>

## 3. How to Build

The java source is java 1.8+ so any JDK 1.8 or later will work.

Bellow are the steps to install the JDK and set the JAVA_HOME environment variable:

___Do this if you do not have neither the JDK nor the JAVA_HOME environment variable set up.___

+ Download the JDK from the following link: https://www.oracle.com/java/technologies/downloads/#java17

+ Install the JDK by running the downloaded file.

+ Set the JAVA_HOME environment variable by following the steps below:

  + Open the Start Panel.

  + Search for "Edit the system environment variables" and click on it.

  + Click on the "Environment Variables" button (bottom left).

  + In the "System Variables" section, click on the "New" button.

  + In the "Variable Name" field, type "JAVA_HOME".

  + In the "Variable Value" field, type the path to the JDK installation folder. For example: "C:\Program Files\Java\jdk-17".

  + Click on the "OK" button.

You can either choose to build all or build each module individually.

To build the project as a whole, start by opening a terminal (command line) and navigate to the "scripts" folder inside the project directory. Then, execute the following command:

        build-all.bat

To build each module individually, start by opening a terminal (command line) and navigate to the "scripts" folder inside the project directory. Then, execute one of the following commands:

        build-user.bat (user = student, teacher or manager)

        or

        build-board-xxx.bat (xxx = client or server)

## 4. How to Run

This script is responsible for running an individual app.

Make sure you are in the "scripts" folder inside the project directory.

**User app**

To run the app, you only need to replace the "user" word in the following command with the user type you want to run.

        run-user.bat (user = student, teacher or manager)

**Board app**

To run the app, you only need to replace the "xxx" word in the following command with the board type you want to run.

        run-board-xxx.bat (xxx = client or server)

## 5. How to Execute Tests

To execute the tests, start by opening the command line and navigate to the "scripts" folder inside the project directory. Then, execute the following commands:

        test-all.bat


## 6. How to Install/Deploy into Another Machine (or Virtual Machine)

To install/deploy the project into another machine (or virtual machine) you need to be in the project directory and execute the following commands:

        build-user.bat (user = student, teacher or manager)

        run-user.bat (user = student, teacher or manager) 

# <span style="color: forestgreen">Linux</span>

## 3. How to Build

To build the project, start by opening a linux terminal and navigate to the "scripts" folder inside the project directory. Then, execute the following commands:

        sudo chmod 755 *.sh

        sudo ./build-all.sh

## 4. How to Run

**User app**

This script is responsible for running an individual user app. To run the app, you only need to replace the "user" word in the following command with the user type you want to run (student, teacher or manager).

        sudo ./run-user.sh

**Board app**

This script is responsible for running an individual board app.
To run the app, you only need to replace the "xxx" word in the following command with the board type you want to run
(client or server).

        sudo ./run-board-xxx.sh

## 5. How to Execute Tests

To execute the tests, start by opening a linux terminal and navigate to the "scripts" folder inside the project directory. Then, execute the following commands:

        sudo ./test-all.sh

## 6. How to Install/Deploy into Another Machine (or Virtual Machine)

To install/deploy the project into another machine (or virtual machine),
you need to be in the "scripts" folder in the project directory and execute the following command:

        sudo chmod 755 *.sh
  
        sudo ./build-user.sh (user = student, teacher or manager)

        sudo ./run-user.sh (user = student, teacher or manager)

        or
    
        sudo chmod 755 *.sh

        sudo ./build-board-xxx.sh (xxx = client or server)

        sudo ./run-board-xxx.sh (xxx = client or server)

---

# <span style="color: cornflowerblue">Shared Board App</span>

## 3. How to Build

        The build process was previously explained in the "How to Build" section in Windows.

## 4. How to Run

          The run process was previously explained in the "How to Run" section in Windows.

## 5. How to Install/Deploy into Another Machine (or Virtual Machine)

*As this is the shared board app, it is necessary to run the server app before running the browser.*

**Disclaimer**

*Currently, the user is forced to run the server app before being able to use the Shared Board App, this due to the fact
that no server is hosting that same application.
In the future if it was required to deploy the solution to a server, it 
would only require the team to define that server ip and the port number.*

        build-board-server.bat

        run-board-server.bat 

*After running the board server, use your favorite browser and type in "localhost:8080" in the URL bar;
then you should be able to 
use the Shared Board App and all of its features effortlessly.*