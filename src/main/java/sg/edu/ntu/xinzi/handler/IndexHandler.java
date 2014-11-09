package sg.edu.ntu.xinzi.handler;

import fi.iki.elonen.NanoHTTPD;

public class IndexHandler extends RequestHandler {
    @Override
    public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) throws RequestUnhandledException {
        return responseFile("index.html");
    }
}
