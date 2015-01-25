package sg.edu.ntu.xinzi.handler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import org.apache.commons.logging.Log;
import sg.edu.ntu.xinzi.util.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class RequestHandler {
    private static Log log = Logger.getLogger();

    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";
    public static final String MIME_JSON = "application/json";
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

    public abstract Response getResponse(IHTTPSession session) throws RequestUnhandledException;

    protected Response reposeString(String message) {
        return new Response(message);
    }

    protected Response responseNotFound() {
        return new Response(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Error 404.");
    }

    protected Response responseForbiddenResponse(String s) {
        return new Response(Response.Status.FORBIDDEN, NanoHTTPD.MIME_PLAINTEXT, "FORBIDDEN: " + s);
    }

    protected Response responseInternalErrorResponse(String s) {
        return new Response(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "INTERNAL ERROR: " + s);
    }

    protected Response responseFile(String fileName) {
        try {
            URL resource = getClass().getResource("/web/" + fileName);
            InputStream is = getClass().getResourceAsStream("/web/" + fileName);
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
            log.info("Serve file \"" + fileName + "\".");
            return response;
        } catch (IOException e) {
            log.warn("File not found: " + fileName);
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

    protected Response responseJson(ObjectNode node) {
        return new Response(Response.Status.OK, MIME_JSON, node.toString());
    }

    protected Response responseRedirect(String url) {
        Response response = new Response(Response.Status.REDIRECT, NanoHTTPD.MIME_HTML, "<html><body><a href=\"" +
                url + "\">Redirected</a></body></html>");
        response.addHeader("Location", url);
        return response;
    }

    public static class RequestUnhandledException extends Exception{};
}
