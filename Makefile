NATIVE_CLASS_NAME = filetransfer.encryption.TEAEncryption
NATIVE_HEADER_NAME = filetransfer_encryption_TEAEncryption.h
NATIVE_METHOD_LOCATION = ./src/filetransfer/encryption

LIB_OUTPUT = ./libs
LIBNAME = libTeaEncryption
LIBSOURCE = ./src/filetransfer/encryption/tea_encryption.c

SOURCE_PATH = ./src
OUTPUT_PATH = ./out/production/project2

SERVER_MAIN = ./src/filetransfer/ServerMain.java
CLIENT_MAIN = ./src/filetransfer/ClientMain.java
REGISTER_MAIN = ./src/filetransfer/RegisterUserMain.java

darwin:
	mkdir -p $(LIB_OUTPUT)
	mkdir -p $(OUTPUT_PATH)
	javac -sourcepath $(SOURCE_PATH) -d $(OUTPUT_PATH) $(SERVER_MAIN) $(CLIENT_MAIN) $(REGISTER_MAIN)
	javah -classpath  $(OUTPUT_PATH) $(NATIVE_CLASS_NAME)
	mv ./$(NATIVE_HEADER_NAME) $(NATIVE_METHOD_LOCATION)/$(NATIVE_HEADER_NAME)
	gcc -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin -shared -fpic -o $(LIBNAME).dylib $(LIBSOURCE)

linux:
	mkdir -p $(LIB_OUTPUT)
	mkdir -p $(OUTPUT_PATH)
	javac -sourcepath $(SOURCE_PATH) -d $(OUTPUT_PATH) $(SERVER_MAIN) $(CLIENT_MAIN) $(REGISTER_MAIN)
	javah -classpath  $(OUTPUT_PATH) $(NATIVE_CLASS_NAME)
	mv ./$(NATIVE_HEADER_NAME) $(NATIVE_METHOD_LOCATION)/$(NATIVE_HEADER_NAME)
	gcc -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -shared -fpic -o $(LIBNAME).so $(LIBSOURCE)


clean:
	rm -rf $(OUTPUT_PATH)/filetransfer *.h