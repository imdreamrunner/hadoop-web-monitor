package sg.edu.ntu.xinzi.server;

import org.apache.commons.logging.Log;
import sg.edu.ntu.xinzi.mapreduce.NotDriver;
import sg.edu.ntu.xinzi.util.Logger;

import java.io.IOException;

public class WebMonitor {
    private static Log log = Logger.getLogger();
    private static Server server;

    public static void main(String[] args) {
        log.info("Welcome to Web Monitor.");
        int port = 40080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                log.error("\"" + args[0] + "\" cannot be as port number");
                e.printStackTrace();
            }
        }


        NotDriver.run();

//        server = new Server(port);
//
//        startServer();
//        System.out.println("Server is started on 0.0.0.0:" + port + ". Press enter to stop the server.");
//
//        try {
//            System.in.read();
//        } catch (Throwable ignored) {
//        }
//
//        stopServer();
//        System.out.println("Server is stopped. Thank you for using.");

        System.exit(0);
    }

    private static void startServer() {
        log.info("Trying to start server.");
        if (server.isAlive()) {
            log.warn("Cannot start server: Server is alive");
            return;
        }
        try {
            server.start();
        } catch (IOException e) {
            log.error("Cannot start server: IOException");
            e.printStackTrace();
        }
        log.info("Server started.");
    }

    private static void stopServer() {
        log.info("Trying to stop server.");
        server.stop();
        log.info("Server stopped.");
    }
}
