package raf.web.logger;

import java.util.logging.Logger;

public class LogHelper {

    private static volatile LogHelper instance;

    private LogHelper() {}

    public static LogHelper getInstance() {
        if (instance == null) {
            synchronized (LogHelper.class) {
                if (instance == null) {
                    instance = new LogHelper();
                }
            }
        }
        return instance;
    }

    public void logInfo(Class<?> clazz, String message) {
        Logger.getLogger(clazz.getName()).info(clazz.getName() + " - " +message);
    }

}
