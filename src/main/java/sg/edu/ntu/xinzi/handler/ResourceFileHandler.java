package sg.edu.ntu.xinzi.handler;

import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import org.apache.commons.logging.Log;
import sg.edu.ntu.xinzi.util.Logger;

import java.util.logging.Level;

public class ResourceFileHandler extends RequestHandler {
    private static Log log = Logger.getLogger();

    @Override
    public Response getResponse(IHTTPSession session) {
        String uri = session.getUri();
        if (uri.equals("/")) {
            return responseRedirect("./index.html");
        }
        String fileName = uri.substring(1);
        log.info("Searching for file \"" + fileName + "\" on static folder.");
        return responseFile(fileName);
    }
}
