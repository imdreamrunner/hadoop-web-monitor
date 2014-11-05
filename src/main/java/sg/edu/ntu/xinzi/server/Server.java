package sg.edu.ntu.xinzi.server;

import fi.iki.elonen.NanoHTTPD;
import sg.edu.ntu.xinzi.util.Log;

import java.io.File;
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
        Map<String, String> header = session.getHeaders();
        Map<String, String> parms = session.getParms();

        logger.log(Level.FINE, method + " '" + uri + "' ");

        if (uri.equals("/")) {
            return reposeString("Hello World!");
        }

        if (uri.matches("^/static/.*")) {
            String fileName = uri.substring(uri.indexOf("/", 1) + 1);
            logger.log(Level.FINE, "Searching for file \"" + fileName + "\" on static folder.");
            try {
                File resourceFile = new File(getClass().getResource("/static/" + fileName).getFile());
                return responseFile(resourceFile);
            } catch (NullPointerException npe) {
                logger.log(Level.WARNING, "File not found: " + fileName);
                return responseNotFound();
            }
        }

        return responseNotFound();
    }

    private Response reposeString(String message) {
        return new Response(message);
    }

    private Response responseNotFound() {
        return new Response(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Error 404.");
    }

    private Response responseForbiddenResponse(String s) {
        return new Response(Response.Status.FORBIDDEN, NanoHTTPD.MIME_PLAINTEXT, "FORBIDDEN: " + s);
    }

    private Response responseInternalErrorResponse(String s) {
        return new Response(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "INTERNAL ERROR: " + s);
    }

    private Response responseFile(File file) {
        return reposeString("Serve File!");
    }

}
