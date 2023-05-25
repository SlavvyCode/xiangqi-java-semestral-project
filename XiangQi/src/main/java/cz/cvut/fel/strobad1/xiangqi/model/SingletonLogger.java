package cz.cvut.fel.strobad1.xiangqi.model;

// import the Logger class
import java.util.logging.Logger;

public class SingletonLogger {

    // private static variable of the same class that is the only instance of the class
    private static final SingletonLogger instance = new SingletonLogger();

    // private static variable of type Logger that is the logger object for this class
    private static final Logger logger = Logger.getLogger(SingletonLogger.class.getName());

    // private constructor to avoid client applications using the constructor
    private SingletonLogger() {}

    // public static method that returns the instance of the class
    public static SingletonLogger getInstance() {
        return instance;
    }

    // a method to get a logger object
    public Logger getLogger() {
        // return the logger variable
        return logger;
    }
}
