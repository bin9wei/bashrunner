Introduction
===========================

Bashrunner is a Java app support run bash script.

How it works
---------------
* To run deploy.sh, start with vm options `-Daction=deploy`
* To run rollback.sh, start with vm options `-Daction=rollback`
* Sample deploy.sh is to create a test file `testBash.txt` in location `$HOME`. rollback.sh is to remove the file.

Running locally
---------------

* via IDE: run RunLocal class
* via command line:
```
## to run deploy.sh
java -jar -Daction=deploy bashrunner-1.0-SNAPSHOT.jar
## to run rollback.sh
java -jar -Daction=rollback bashrunner-1.0-SNAPSHOT.jar
```
