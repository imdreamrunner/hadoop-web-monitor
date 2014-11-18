package sg.edu.ntu.xinzi.handler;

import fi.iki.elonen.NanoHTTPD;
import sg.edu.ntu.xinzi.mapreduce.NotDriver;

public class JobRunHandler extends RequestHandler {
    @Override
    public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) throws RequestUnhandledException {
        NotDriver.run();
        return reposeString("Job Started");
    }
}
