package hw12.server;

import org.eclipse.jetty.server.Server;

public class UsersWebServer implements WebServer {
    private final Server server;

    public UsersWebServer(int port) {
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

}
