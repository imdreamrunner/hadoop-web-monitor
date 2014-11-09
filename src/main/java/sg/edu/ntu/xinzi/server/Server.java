package sg.edu.ntu.xinzi.server;

import fi.iki.elonen.NanoHTTPD;
import sg.edu.ntu.xinzi.handler.IndexHandler;
import sg.edu.ntu.xinzi.handler.ResourceFileHandler;
import sg.edu.ntu.xinzi.util.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends NanoHTTPD {
    private static Logger logger = Log.getLogger();

    public int port;

    private static final Router router = new Router() {{
        add(Method.GET, "^/$", IndexHandler.class);
        add(Method.GET, "^/static/.*", ResourceFileHandler.class);
    }};

    public Server(int port) {
        super(port);
        this.port = port;
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();

        logger.log(Level.FINE, method + " '" + uri + "' ");

        return router.handle(session);
    }


}
