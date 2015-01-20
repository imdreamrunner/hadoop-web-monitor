package sg.edu.ntu.xinzi.handler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fi.iki.elonen.NanoHTTPD;
import sg.edu.ntu.xinzi.mapreduce.NotDriver;
import sg.edu.ntu.xinzi.util.Json;

public class JobRunHandler extends RequestHandler {
    @Override
    public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) throws RequestUnhandledException {
//        NotDriver.run();
        ObjectNode out = Json.newObject();
        out.put("error", 0);
        return responseJson(out);
    }
}
