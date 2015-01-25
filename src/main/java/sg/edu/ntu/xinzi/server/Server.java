package sg.edu.ntu.xinzi.server;

import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.logging.Log;
import sg.edu.ntu.xinzi.handler.JobRunHandler;
import sg.edu.ntu.xinzi.handler.ResourceFileHandler;
import sg.edu.ntu.xinzi.util.Logger;

public class Server extends NanoHTTPD {
    private static Log log = Logger.getLogger();

    public int port;

    private static final Router router = new Router() {{
        add(Method.GET, "^/.*", ResourceFileHandler.class);
        add(Method.GET, "^/run", JobRunHandler.class);
    }};

    public Server(int port) {
        super(port);
        this.port = port;
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();

        log.info(method + " '" + uri + "' ");

        return router.handle(session);
    }


}
