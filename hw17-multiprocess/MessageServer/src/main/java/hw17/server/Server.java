package hw17.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private void clientHandler(Socket clientSocket) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String input = null;
            while (!"stop".equals(input)) {
                input = in.readLine();
                if (input != null) {
                    logger.info("from client: {} ", input);
                    out.println("echo:" + input);
                }
            }
            clientSocket.close();
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    private void clientHandler1(Socket clientSocket){
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String input = null;
            while (!"stop".equals(input)) {
                input = in.readLine();
                if (input != null) {
                    // send ot message system
                }
            }
            clientSocket.close();
        } catch (Exception ex) {
            logger.error("error", ex);
        }
    }

    public void start(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("waiting for client connection");
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> clientHandler(clientSocket));
            }
        } catch (Exception ex) {
            logger.error("error", ex);
        }
        executor.shutdown();
    }
}

