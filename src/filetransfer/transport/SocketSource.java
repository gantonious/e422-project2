package filetransfer.transport;

import filetransfer.InputOutputSource;
import filetransfer.shared.exceptions.SocketIOException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by George on 2017-04-09.
 */
public class SocketSource implements InputOutputSource {
    private Socket socket;
    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;

    public SocketSource(Socket socket) {
        this.socket = socket;

        try {
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
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

    // TODO: maybe refactor if time permits
    private byte[] readFromBuffer(int total) throws IOException {
        byte[] output = new byte[total];

        int amountRead = bufferedInputStream.read(output, 0, total);
        int totalRead = amountRead;
        int totalToRead = total - amountRead;

        while (amountRead > 0 && totalRead != total) {
            amountRead = bufferedInputStream.read(output, totalRead, totalToRead);
            if (amountRead > 0) {
                totalRead += amountRead;
                totalToRead -= amountRead;
            }
        }

        return output;
    }

    private void writeToBuffer(byte[] data) throws IOException {
        bufferedOutputStream.write(data);
        bufferedOutputStream.flush();
    }
}