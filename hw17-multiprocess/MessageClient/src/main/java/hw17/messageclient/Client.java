package hw17.messageclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private final int port;
    private final String host;
    private final String name;
    private final Thread thread;
    private SocketChannel channel;
    private boolean isConnected;

    public Client(String host, int port, String name) {
        this.port = port;
        this.host = host;
        this.name = name;
        thread = new Thread(() -> handler());
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void connect() {
        thread.start();
    }

    private void handler() {
        try {
            try (SocketChannel socketChannel = SocketChannel.open()) {
                channel = socketChannel;
                socketChannel.configureBlocking(false);

                final var inetSocketAddress = new InetSocketAddress(host, port);
                socketChannel.connect(inetSocketAddress);
                while (!socketChannel.finishConnect()) {
                    logger.info("connect to {}", inetSocketAddress.toString());
                }

                isConnected = true;

                handleResponse(socketChannel);
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    public void send(String request) {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        buffer.put(request.getBytes());
        buffer.flip();
        logger.info("sending to server");
        try {
            channel.write(buffer);
        } catch (IOException e) {
            logger.error("Error send request to server ");
        }
    }

    private void handleResponse(SocketChannel socketChannel) throws IOException {
        final Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        while (!Thread.currentThread().isInterrupted()) {
            logger.info("waiting for response");
            if (selector.select() > 0) { //This method performs a blocking
                final Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    final SelectionKey key = keys.next();
                    if (key.isReadable()) {
                        processServerResponse((SocketChannel) key.channel());
                        keys.remove();
                    }
                }
            }
        }
    }

    private void processServerResponse(SocketChannel socketChannel) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(256);
        final StringBuilder response = new StringBuilder();
        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            final String responsePart = StandardCharsets.UTF_8.decode(buffer).toString();
            response.append(responsePart);
            buffer.flip();
        }
        logger.info("response: {}", response);
    }

}
