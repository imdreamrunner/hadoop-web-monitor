package sg.edu.ntu.xinzi.handler;

import fi.iki.elonen.NanoHTTPD;

public class DefaultHandler extends RequestHandler {
    @Override
    public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) throws RequestUnhandledException {
        return responseNotFound();
    }
}
