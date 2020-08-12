package hw17.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.messageservice.MessageService;
import hw17.messageservice.handler.MessageHandler;
import ru.otus.messagesystem.message.MessageType;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final MessageService messageService;

    public Server(MessageService messageService) {
        this.messageService = messageService;
        final var handler = new MessageHandler();
        this.messageService.addHandler(MessageType.USER_DATA, handler);
    }

    private void clientHandler1(Socket clientSocket) {
        final var socketHanlder = new SocketHandler(messageService);
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String input = null;
            while (!"stop".equals(input)) {
                input = in.readLine();
                if (input != null) {
                    out.println(socketHanlder.recive(input));
                }
            }
            clientSocket.close();
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("waiting for client connection");
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                logger.info("connected {}", clientSocket);
                executor.submit(() -> clientHandler1(clientSocket));
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
        executor.shutdown();
    }

}
