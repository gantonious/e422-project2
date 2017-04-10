NATIVE_CLASS_NAME = filetransfer.encryption.TEAEncryption

SOURCE_PATH = ./src
OUTPUT_PATH = ./out/production/project2

SERVER_MAIN = ./src/filetransfer/ServerMain.java
CLIENT_MAIN = ./src/filetransfer/ClientMain.java
REGISTER_MAIN = ./src/filetransfer/RegisterUserMain.java
PRIME = ./src/filetransfer/PrimeGenerator.java

all:
	javac -sourcepath $(SOURCE_PATH) -d $(OUTPUT_PATH) $(SERVER_MAIN) $(CLIENT_MAIN) $(REGISTER_MAIN) $(PRIME)
	javah -classpath  $(OUTPUT_PATH) $(NATIVE_CLASS_NAME)

clean:
	rm -rf $(OUTPUT_PATH)/filetransfer *.h