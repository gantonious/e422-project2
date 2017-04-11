NATIVE_CLASS_NAME = filetransfer.encryption.TEAEncryption
NATIVE_HEADER_NAME = filetransfer_encryption_TEAEncryption.h
NATIVE_METHOD_LOCATION = ./src/filetransfer/encryption

LIBNAME = libTeaEncryption
LIBSOURCE = ./src/filetransfer/encryption/tea_encryption.c

SOURCE_PATH = ./src
OUTPUT_PATH = ./out/production/project2
DOWNLOADS_PATH = ./downloads
SRV_PATH = ./srv

SERVER_MAIN = ./src/filetransfer/ServerMain.java
CLIENT_MAIN = ./src/filetransfer/ClientMain.java
REGISTER_MAIN = ./src/filetransfer/RegisterUserMain.java

darwin: java
	gcc -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin -shared -fpic -o $(LIBNAME).dylib $(LIBSOURCE)

linux: java
	gcc -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -shared -fpic -o $(LIBNAME).so $(LIBSOURCE)

java:
	mkdir -p $(OUTPUT_PATH)
	mkdir -p $(DOWNLOADS_PATH)
	mkdir -p $(SRV_PATH)
	javac -sourcepath $(SOURCE_PATH) -d $(OUTPUT_PATH) $(SERVER_MAIN) $(CLIENT_MAIN) $(REGISTER_MAIN)
	javah -classpath  $(OUTPUT_PATH) $(NATIVE_CLASS_NAME)
	mv ./$(NATIVE_HEADER_NAME) $(NATIVE_METHOD_LOCATION)/$(NATIVE_HEADER_NAME)

clean:
	rm -rf $(OUTPUT_PATH)/filetransfer *.h