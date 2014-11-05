package sg.edu.ntu.xinzi.util;

import java.util.logging.Logger;

public class Log {
    public static Logger getLogger() {
        // http://stackoverflow.com/questions/80692/java-logger-that-automatically-determines-callers-class-name
        // We use the third stack element; second is this method, first is .getStackTrace()
        StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
        return Logger.getLogger(myCaller.getClassName());
    }
}
