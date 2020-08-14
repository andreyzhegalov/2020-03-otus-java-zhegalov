package hw17.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.messageservice.MessageService;
import hw17.messageservice.handler.MessageHandler;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final MessageService messageService;
    private final Map<SocketHandler, Socket> clientMap = new ConcurrentHashMap<>();

    public Server(MessageService messageService) {
        this.messageService = messageService;
        final var handler = new MessageHandler(clientMap);
        this.messageService.addHandler(handler);
    }

    private void clientHandler(Socket clientSocket) {
        final var socketHanlder = new SocketHandler(messageService);
        clientMap.put(socketHanlder, clientSocket);
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
                executor.submit(() -> clientHandler(clientSocket));
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
        executor.shutdown();
    }

}
