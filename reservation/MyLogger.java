package reservation;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger {
    private Logger logger;
    private ConsoleHandler handler = new ConsoleHandler();
    private String className;
    private final boolean DEBUG = false;
    // private final Level LEVEL = Level.FINEST;
    private final Level LEVEL = Level.INFO;
    
    public MyLogger(String className) {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
        //https://stackoverflow.com/a/5003248

        this.logger = Logger.getLogger(className);
        this.logger.setLevel(this.LEVEL);
        this.handler.setLevel(this.LEVEL);
        this.handler.setFormatter(new MyFormatter());

        this.logger.addHandler(this.handler);

        this.className = className;
    }

    private String debugLog(String msg) {
        return DEBUG ? this.className+"➔ "+msg : msg;
    }

    public void log(Level level, String msg){
        this.logger.log(level, this.debugLog(msg));
    }

    public void log(Level level, String msg, String error){
        this.logger.log(level, this.debugLog(msg), error);
    }
}