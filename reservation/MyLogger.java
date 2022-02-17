package reservation;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger {
    private Logger logger;
    private ConsoleHandler handler = new ConsoleHandler();
    private final Level level = Level.FINER;
    
    public MyLogger(String className) {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
        //https://stackoverflow.com/a/5003248

        this.logger = Logger.getLogger(className);
        this.logger.setLevel(this.level);
        this.handler.setLevel(this.level);
        this.handler.setFormatter(new MyFormatter());
        this.logger.removeHandler(handler);

        this.logger.addHandler(this.handler);
    }

    public void log(Level level, String msg){
        this.logger.log(level, msg);
    }

    public void log(Level level, String msg, String error){
        this.logger.log(level, msg, error);
    }
}