package net.lighting.flow.util;

import org.apache.log4j.Logger;

public class LogUtil {

    private static Logger rootLog = Logger.getLogger(LogUtil.class);
    
    public static void info(Object...args) {
        if (rootLog.isInfoEnabled()) {
            rootLog.info(joinWith(",", args));
        }
    }
    
    public static void debug(Object...args) {
        if (rootLog.isDebugEnabled()) {
            rootLog.debug(joinWith(getStackTrace(3), ",", args));
        }
    }
    
    public static void locate(Object...args) {
        if (rootLog.isInfoEnabled()) {
            rootLog.info(joinWith(getStackTrace(3), ",", args));
        }
    }
    
    public static void error(String msg, Exception e) {
        rootLog.error(msg, e);
    }
    
    public static void error(String msg) {
        rootLog.error(msg);
    }
    
    public static String join(Object...args) {
        StringBuilder sb = new StringBuilder();
        for (Object o : args) {
            sb.append(o);
        }
        return sb.toString();
    }
    
    public static String joinWith(String prefix, String connecter, Object...args) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(" ");
        if (args == null || args.length == 0) return sb.toString();
        sb.append(args[0]);
        for (int i=1,len=args.length; i<len; i++) {
            sb.append(connecter).append(args[i]);
        }
        return sb.toString();
    }
    
    public static String joinWith(String connecter, Object...args) {
        StringBuilder sb = new StringBuilder();
        if (args == null || args.length == 0) return sb.toString();
        sb.append(args[0]);
        for (int i=1,len=args.length; i<len; i++) {
            sb.append(connecter).append(args[i]);
        }
        return sb.toString();
    }
    
    public static String getStackTrace(int level) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        if (stacks.length > level) {
            return stacks[level].toString();
        }
        return "";
    }
}
