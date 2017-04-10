package filetransfer.transport;

import filetransfer.InputOutputSource;
import filetransfer.shared.exceptions.SocketIOException;

import java.io.*;
import java.net.Socket;

/**
 * Created by George on 2017-04-09.
 */
public class SocketSource implements InputOutputSource {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public SocketSource(Socket socket) {
        this.socket = socket;

        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new SocketIOException(e.getMessage());
        }
    }

    @Override
    public byte[] read(int total) {
        try {
            return readFromBuffer(total);
        } catch (IOException e) {
            throw new SocketIOException(e.getMessage());
        }
    }

    @Override
    public void write(byte[] data) {
        try {
            writeToBuffer(data);
        } catch (IOException e) {
            throw new SocketIOException(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new SocketIOException(e.getMessage());
        }
    }

    private byte[] readFromBuffer(int total) throws IOException {
        byte[] output = new byte[total];
        dataInputStream.readFully(output);

        return output;
    }

    private void writeToBuffer(byte[] data) throws IOException {
        dataOutputStream.write(data);
    }
}