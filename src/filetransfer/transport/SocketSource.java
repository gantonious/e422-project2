package filetransfer.transport;

import filetransfer.InputOutputSource;
import filetransfer.shared.exceptions.SocketIOException;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by George on 2017-04-09.
 */
public class SocketSource implements InputOutputSource {
    private Socket socket;

    public SocketSource(Socket socket) {
        this.socket = socket;
    }

    @Override
    public byte[] read(int total) {
        try {
            return readFrom(socket, total);
        } catch (IOException e) {
            throw new SocketIOException(e.getMessage());
        }
    }

    @Override
    public void write(byte[] data) {
        try {
            writeTo(socket, data);
        } catch (IOException e) {
            throw new SocketIOException(e.getMessage());
        }
    }

    private byte[] readFrom(Socket socket, int total) throws IOException {
        byte[] output = new byte[total];
        socket.getInputStream().read(output);
        return output;
    }

    private void writeTo(Socket socket, byte[] data) throws IOException {
        socket.getOutputStream().write(data);
    }
}