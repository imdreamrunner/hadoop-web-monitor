package sg.edu.ntu.xinzi.util;

import java.util.logging.*;

public class Log {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static Logger getLogger() {
        // http://stackoverflow.com/questions/80692/java-logger-that-automatically-determines-callers-class-name
        // We use the third stack element; second is this method, first is .getStackTrace()
        StackTraceElement myCaller = Thread.currentThread().getStackTrace()[2];
        Logger logger = Logger.getLogger(myCaller.getClassName());
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new SimpleFormatter() {
            public String format(LogRecord record) {
                return "[" + record.getLevel() + "] "
                        + record.getMessage() + " # "
                        + record.getSourceClassName() + " "
                        + record.getSourceMethodName() + "\n";
            }
        });
        logger.addHandler(consoleHandler);
        return logger;
    }
}
