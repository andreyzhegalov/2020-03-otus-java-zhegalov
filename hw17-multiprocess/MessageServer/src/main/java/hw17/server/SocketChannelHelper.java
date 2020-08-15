package hw17.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SocketChannelHelper {
    public static void send(SocketChannel socketChannel, String requestFromClient) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(5);
        byte[] response = requestFromClient.getBytes();
        for (byte b : response) {
            buffer.put(b);
            if (buffer.position() == buffer.limit()) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.flip();
            }
        }
        if (buffer.hasRemaining()) {
            buffer.flip();
            socketChannel.write(buffer);
        }
    }

    public static String recive(SocketChannel socketChannel) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(256);
        final StringBuilder inputBuffer = new StringBuilder(100);

        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            final String input = StandardCharsets.UTF_8.decode(buffer).toString();
            buffer.flip();
            inputBuffer.append(input);
        }
        final String requestFromClient = inputBuffer.toString().replace("\n", "").replace("\r", "");
        return requestFromClient;
    }

}

