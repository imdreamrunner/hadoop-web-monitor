package sg.edu.ntu.xinzi.server;

import sg.edu.ntu.xinzi.mapreduce.NotDriver;
import sg.edu.ntu.xinzi.util.Log;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebMonitor {
    private static Logger logger = Log.getLogger();
    private static Server server;

    public static void main(String[] args) {
        System.out.println("Welcome to Web Monitor.");
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.log(Level.SEVERE, "\"" + args[0] + "\" cannot be as port number");
                e.printStackTrace();
            }
        }

        server = new Server(port);

        startServer();
        System.out.println("Server is started on 0.0.0.0:" + port + ". Press enter to stop the server.");

        try {
            System.in.read();
        } catch (Throwable ignored) {
        }

        stopServer();
        System.out.println("Server is stopped. Thank you for using.");

        System.exit(0);
    }

    private static void startServer() {
        logger.log(Level.INFO, "Trying to start server.");
        if (server.isAlive()) {
            logger.log(Level.WARNING, "Cannot start server: Server is alive");
            return;
        }
        try {
            server.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot start server: IOException");
            e.printStackTrace();
        }
        logger.log(Level.INFO, "Server started.");
    }

    private static void stopServer() {
        logger.log(Level.INFO, "Trying to stop server.");
        server.stop();
        logger.log(Level.INFO, "Server stopped.");
    }
}
