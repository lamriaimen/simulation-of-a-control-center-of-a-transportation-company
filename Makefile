CC = javac -cp ./src
TEST = javac -cp build:lib/junit-platform-console-standalone-1.10.1.jar
JAVADOC = javadoc -d docs -sourcepath @sourcefiles.txt

all:
	make clean
	make classes
	make tests

classes:
	$(CC) -d build src/rentable/*.java src/station/*.java src/controlCenter/*.java
	
tests:
	javac -d build_tests -cp tests:build/:lib/junit-platform-console-standalone-1.10.1.jar tests/controlCenter/*.java tests/rentable/*.java tests/station/*.java

run_tests:
	make all
	java -jar lib/junit-platform-console-standalone-1.10.1.jar --class-path build_tests:build --scan-class-path

jar:
	make all
	jar cfe controlCenter.jar controlCenter.ControlCenter -C build controlCenter -C build rentable -C build station

docs:
	javadoc -d docs -sourcepath src -subpackages controlCenter station rentable 

clean:
	rm -rf build/*
	rm -rf tests/controlCenter/*.class tests/rentable/*.class tests/station/*.class
	rm -rf build_tests
	rm -rf docs

.PHONY: all classes tests run_tests jar docs clean