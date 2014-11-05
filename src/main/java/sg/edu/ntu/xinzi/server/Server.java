package sg.edu.ntu.xinzi.server;

import fi.iki.elonen.NanoHTTPD;
import sg.edu.ntu.xinzi.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends NanoHTTPD {
    private static Logger logger = Log.getLogger();

    public int port;

    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";
    private static final Map<String, String> MIME_TYPES = new HashMap<String, String>() {{
        put("css", "text/css");
        put("htm", "text/html");
        put("html", "text/html");
        put("xml", "text/xml");
        put("java", "text/x-java-source, text/java");
        put("md", "text/plain");
        put("txt", "text/plain");
        put("asc", "text/plain");
        put("gif", "image/gif");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("png", "image/png");
        put("mp3", "audio/mpeg");
        put("m3u", "audio/mpeg-url");
        put("mp4", "video/mp4");
        put("ogv", "video/ogg");
        put("flv", "video/x-flv");
        put("mov", "video/quicktime");
        put("swf", "application/x-shockwave-flash");
        put("js", "application/javascript");
        put("pdf", "application/pdf");
        put("doc", "application/msword");
        put("ogg", "application/x-ogg");
        put("zip", "application/octet-stream");
        put("exe", "application/octet-stream");
        put("class", "application/octet-stream");
    }};

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
            return responseFile(fileName);
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

    private Response responseFile(String fileName) {
        try {
            URL resource = getClass().getResource("/static/" + fileName);
            InputStream is = getClass().getResourceAsStream("/static/" + fileName);
            if (resource == null || is.available() == 0) {
                throw new FileNotFoundException();
            }
            // File file = new File(resource.getFile());  // cannot use for jar
            // calculate ETag
            int hashCode = (fileName + resource.openConnection().getLastModified()).hashCode();
            String eTag = Integer.toHexString(hashCode);
            // create response
            Response response = new Response(Response.Status.OK,
                    getMimeTypeForFile(fileName),
                   is );
            // response.addHeader("Content-Length", "" + file.length());
            response.addHeader("ETag", eTag);
            logger.log(Level.INFO, "Serve file \"" + fileName + "\".");
            return response;
        } catch (IOException e) {
            logger.log(Level.WARNING, "File not found: " + fileName);
            // e.printStackTrace();
            return responseNotFound();
        }
    }

    private String getMimeTypeForFile(String fileName) {
        int dot = fileName.lastIndexOf('.');
        String extension = fileName.substring(dot + 1).toLowerCase();
        String mime = null;
        if (dot >= 0) {
            mime = MIME_TYPES.get(extension);
        }
        return mime == null ? MIME_DEFAULT_BINARY : mime;
    }

}
