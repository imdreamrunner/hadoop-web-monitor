package sg.edu.ntu.xinzi.server;

import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.logging.Log;
import sg.edu.ntu.xinzi.handler.DefaultHandler;
import sg.edu.ntu.xinzi.handler.RequestHandler;
import sg.edu.ntu.xinzi.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Router {
    private static Log log = Logger.getLogger();

    private List<RouteRecord> records = new ArrayList<>();

    public void add(NanoHTTPD.Method method, String uri, Class handler) {
        RouteRecord record = null;
        try {
            record = new RouteRecord(method, uri, handler);
            records.add(record);
        } catch (InvalidHandlerException e) {
            log.error("Invalid handler");
            e.printStackTrace();
        }
    }

    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) {
        log.info("Router trying to handle session ");
        NanoHTTPD.Response response = null;
        for (RouteRecord record : records) {
            if (record.method == session.getMethod() && record.match(session.getUri())) try {
                RequestHandler handler = record.handler.newInstance();
                response = handler.getResponse(session);
                break;
            } catch (InstantiationException|IllegalAccessException e) {
                log.error("Cannot create handler instance.");
                e.printStackTrace();
            } catch (RequestHandler.RequestUnhandledException e) {
                log.info("Request unhandled by handler.");
                e.printStackTrace();
            }
        }
        if (response == null) {
            try {
                response = (new DefaultHandler()).getResponse(session);
            } catch (RequestHandler.RequestUnhandledException e) {
                log.error("Cannot create default handler.");
                e.printStackTrace();
            }
        }
        return response;
    }

    public static class RouteRecord {
        public NanoHTTPD.Method method;
        public String uri;
        public Class<RequestHandler> handler;
        @SuppressWarnings("unchecked")
        public RouteRecord(NanoHTTPD.Method method, String uri, Class handler) throws InvalidHandlerException {
            if (RequestHandler.class.isAssignableFrom(handler)) {
                this.method = method;
                this.uri = uri;
                this.handler = handler;
            } else {
                throw new InvalidHandlerException();
            }
        }
        public boolean match(String uri) {
            return uri.matches(this.uri);
        }
    }

    public static class InvalidHandlerException extends Exception {}
    public static class UriUnmatchedException extends Exception {}
}
