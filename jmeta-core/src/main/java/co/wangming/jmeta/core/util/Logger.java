package co.wangming.jmeta.core.util;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created By WangMing On 2020-03-24
 **/
public class Logger {

    private static ThreadLocal<Messager> messagerThreadLocal = new ThreadLocal<>();

    public static void clean() {
        messagerThreadLocal.remove();
    }

    public static void error(String string, Object... params) {
        Messager messager = getMessager();
        if (messager == null) return;

        string = formatMsg(string, params);

        messager.printMessage(Diagnostic.Kind.ERROR, string);
    }

    private static String formatMsg(String string, Object[] params) {
        for (int i = 0; i < params.length; i++) {
            string = string.replaceFirst("\\{\\}", params[i].toString());
        }
        return string;
    }

    public static void error(String string, Exception e) {
        Messager messager = getMessager();
        if (messager == null) return;

        messager.printMessage(Diagnostic.Kind.ERROR, string + " " + e.getMessage());
    }

    public static void warn(String string, Object... params) {
        Messager messager = getMessager();
        if (messager == null) return;
        string = formatMsg(string, params);
        messager.printMessage(Diagnostic.Kind.WARNING, string);
    }

    public static void info(String string, Object... params) {
        Messager messager = getMessager();
        if (messager == null) return;
        string = formatMsg(string, params);
        messager.printMessage(Diagnostic.Kind.NOTE, string);
    }

    private static Messager getMessager() {
        Messager messager = messagerThreadLocal.get();
        if (messager == null) {
            System.err.println("找不到logger, 返回null");
            return null;
        }
        return messager;
    }

    public static void setMessager(Messager messager) {
        messagerThreadLocal.set(messager);
    }

}
