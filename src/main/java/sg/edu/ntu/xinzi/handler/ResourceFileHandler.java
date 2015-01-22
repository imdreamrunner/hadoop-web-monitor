package sg.edu.ntu.xinzi.handler;

import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import sg.edu.ntu.xinzi.util.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceFileHandler extends RequestHandler {
    private static Logger logger = Log.getLogger();

    @Override
    public Response getResponse(IHTTPSession session) {
        String uri = session.getUri();
        if (uri.equals("/")) {
            return responseRedirect("./index.html");
        }
        String fileName = uri.substring(1);
        logger.log(Level.FINE, "Searching for file \"" + fileName + "\" on static folder.");
        return responseFile(fileName);
    }
}
