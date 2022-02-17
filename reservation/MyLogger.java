package reservation;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
    private Logger logger;
    private ConsoleHandler handler = new ConsoleHandler();
    private final Level level = Level.FINEST;
    
    public MyLogger(String className) {
        this.logger = Logger.getLogger(className);
        this.logger.setLevel(this.level);
        this.handler.setLevel(this.level);
        this.logger.addHandler(this.handler);
        this.handler.setFormatter(new MyFormatter()); 
    }

    public void log(Level level, String msg){
        this.logger.log(level, msg);
    }

    public void log(Level level, String msg, String error){
        this.logger.log(level, msg, error);
    }
}