package reservation;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
    private Logger logger;
    private ConsoleHandler handler = new ConsoleHandler();
    
    public MyLogger(String className) {
        Logger.getLogger(className);
        this.logger.setLevel(Level.FINE);
        this.handler.setLevel(Level.FINE);
        this.logger.addHandler(handler);
    }

    public void log(Level level, String msg){
        this.logger.log(level, msg);
    }

    public void log(Level level, String msg, String error){
        this.logger.log(level, msg, error);
    }
}