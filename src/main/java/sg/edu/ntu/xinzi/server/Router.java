package sg.edu.ntu.xinzi.server;

import fi.iki.elonen.NanoHTTPD;
import sg.edu.ntu.xinzi.handler.DefaultHandler;
import sg.edu.ntu.xinzi.handler.RequestHandler;
import sg.edu.ntu.xinzi.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Router {
    private static Logger logger = Log.getLogger();

    private List<RouteRecord> records = new ArrayList<RouteRecord>();

    public void add(NanoHTTPD.Method method, String uri, Class handler) {
        RouteRecord record = new RouteRecord(method, uri, handler);
        records.add(record);
    }

    public NanoHTTPD.Response handle(NanoHTTPD.IHTTPSession session) {
        logger.log(Level.FINE, "Router trying to handle session ");
        NanoHTTPD.Response response = null;
        for (RouteRecord record : records) {
            if (record.method == session.getMethod() && record.match(session.getUri())) try {
                RequestHandler handler = record.handler.newInstance();
                response = handler.getResponse(session);
                break;
            } catch (InstantiationException|IllegalAccessException e) {
                logger.log(Level.SEVERE, "Cannot create handler instance.");
                e.printStackTrace();
            } catch (RequestHandler.RequestUnhandledException e) {
                logger.log(Level.INFO, "Request unhandled by handler.");
                e.printStackTrace();
            }
        }
        if (response == null) {
            try {
                response = (new DefaultHandler()).getResponse(session);
            } catch (RequestHandler.RequestUnhandledException e) {
                logger.log(Level.SEVERE, "Cannot create default handler.");
                e.printStackTrace();
            }
        }
        return response;
    }

    public static class RouteRecord {
        public NanoHTTPD.Method method;
        public String uri;
        public Class<RequestHandler> handler;
        public RouteRecord(NanoHTTPD.Method method, String uri, Class handler) {
            this.method = method;
            this.uri = uri;
            this.handler = handler;
        }
        public boolean match(String uri) {
            return uri.matches(this.uri);
        }
    }

    public static class InvalidHandlerException extends Exception {}
    public static class UriUnmatchedException extends Exception {}
}
