@echo off

mvn install:install-file -Dfile=lib/ufdw.jar -DgroupId=edu.ufl -DartifactId=ufl -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/j4k-natives-windows-amd64.jar -DgroupId=com.j4k -DartifactId=j4k -Dversion=1.0 -Dpackaging=jar

@echo on