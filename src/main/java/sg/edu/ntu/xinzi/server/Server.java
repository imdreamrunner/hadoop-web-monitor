package sg.edu.ntu.xinzi.server;

import fi.iki.elonen.NanoHTTPD;
import sg.edu.ntu.xinzi.util.Log;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends NanoHTTPD {
    private static Logger logger = Log.getLogger();

    public int port;

    public Server(int port) {
        super(port);
        this.port = port;
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        Map<String, String> parms = session.getParms();

        logger.log(Level.FINE, method + " '" + uri + "' ");

        String msg = "Hello World!";

        return new NanoHTTPD.Response(msg);
    }
}
