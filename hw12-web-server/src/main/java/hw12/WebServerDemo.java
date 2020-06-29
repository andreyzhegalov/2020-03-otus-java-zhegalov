package hw12;

import hw12.server.UsersWebServer;

public class WebServerDemo {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Server port not defined. Setup port argument");
            return;
        }

        // UserAuthService authService = new UserAuthServiceImpl();

        final UsersWebServer usersWebServer = new UsersWebServer(Integer.valueOf(args[0]));
        usersWebServer.start();
        usersWebServer.join();

    }

}
