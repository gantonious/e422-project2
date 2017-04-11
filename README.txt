ECE 422 Project 2
George Antonious (1396458)

Building:

To build on Linux do:
make linux

To build on OS X do:
make darwin

Running:

Before running make sure you include the current directory in you library path:
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.

These instructions are assuming you have ran make, and you are a in the root
directory of the project.

The server will serve files from: ./srv/
The client will download files to: ./downloads/

To register a user the data do:
java -classpath out/production/project2 filetransfer.RegisterUserMain

To launch a client do:
java -classpath out/production/project2 filetransfer.ClientMain

To launch the server do:
java -classpath out/production/project2 filetransfer.ServerMain