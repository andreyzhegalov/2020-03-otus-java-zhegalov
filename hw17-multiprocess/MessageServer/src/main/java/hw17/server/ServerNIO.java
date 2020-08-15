package hw17.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.messageservice.MessageService;
import hw17.messageservice.handler.MessageHandler;

public class ServerNIO {
    private static final Logger logger = LoggerFactory.getLogger(ServerNIO.class);
    private final MessageService messageService;
    private final Map<SocketChannel, SocketHandler> clientsMap = new ConcurrentHashMap<>();

    public ServerNIO(MessageService messageService) {
        this.messageService = messageService;
        final var handler = new MessageHandler(clientsMap);
        this.messageService.addHandler(handler);
    }

    public void start(int port) throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));

            try (Selector selector = Selector.open()) {
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (!Thread.currentThread().isInterrupted()) {
                    logger.info("waiting for client");
                    if (selector.select() > 0) { // This method performs a blocking
                        performIO(selector);
                    }
                }
            }
        }
    }

    private void performIO(Selector selector) throws IOException {
        logger.info("something happened");
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

        while (keys.hasNext()) {
            SelectionKey key = keys.next();
            if (key.isAcceptable()) {
                acceptConnection(key, selector);
            } else if (key.isConnectable()) {
                logger.debug("key isConnectable");
            } else if (key.isReadable()) {
                readWriteClient(key);
            }
            keys.remove();
        }
    }

    private void acceptConnection(SelectionKey key, Selector selector) throws IOException {
        logger.info("accept client connection");
        final ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        final SocketChannel socketChannel = serverSocketChannel.accept(); // The socket channel for the new connection
        logger.debug("New socket channel {}", socketChannel.toString());

        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        clientsMap.put(socketChannel, new SocketHandler(messageService));
    }

    private void readWriteClient(SelectionKey selectionKey) throws IOException {
        logger.info("read from client");
        final SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        try {
            logger.debug("Recives message from socket {}", socketChannel);
            final String requestFromClient = handleRequest(socketChannel);
            if ("stop".equals(requestFromClient.replace("\r\n", ""))) {
                socketChannel.close();
            } else {
                sendResponse(socketChannel, requestFromClient);
            }
        } catch (Exception ex) {
            logger.error("error sending response", ex);
            socketChannel.close();
        }
    }

    private void sendResponse(SocketChannel socketChannel, String requestFromClient) throws IOException{
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

    private String handleRequest(SocketChannel socketChannel) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(256);
        final StringBuilder inputBuffer = new StringBuilder(100);

        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            final String input = StandardCharsets.UTF_8.decode(buffer).toString();
            buffer.flip();
            inputBuffer.append(input);
        }

        final String requestFromClient = inputBuffer.toString().replace("\n", "").replace("\r", "");
        logger.info("requestFromClient: {} ", requestFromClient);

        return sendToMessageSystemClient(socketChannel, requestFromClient);
    }

    private String sendToMessageSystemClient(SocketChannel socketChannel, String message){
        final var clientHander = clientsMap.get(socketChannel);
        return clientHander.recive(message);
    }
}
