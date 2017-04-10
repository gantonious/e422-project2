NATIVE_CLASS_NAME = filetransfer.encryption.TEAEncryption
NATIVE_HEADER_NAME = filetransfer_encryption_TEAEncryption.h
NATIVE_METHOD_LOCATION = ./src/filetransfer/encryption

SOURCE_PATH = ./src
OUTPUT_PATH = ./out/production/project2

SERVER_MAIN = ./src/filetransfer/ServerMain.java
CLIENT_MAIN = ./src/filetransfer/ClientMain.java
REGISTER_MAIN = ./src/filetransfer/RegisterUserMain.java

all:
	javac -sourcepath $(SOURCE_PATH) -d $(OUTPUT_PATH) $(SERVER_MAIN) $(CLIENT_MAIN) $(REGISTER_MAIN)
	javah -classpath  $(OUTPUT_PATH) $(NATIVE_CLASS_NAME)
	mv ./$(NATIVE_HEADER_NAME) $(NATIVE_METHOD_LOCATION)/$(NATIVE_HEADER_NAME)

clean:
	rm -rf $(OUTPUT_PATH)/filetransfer *.h