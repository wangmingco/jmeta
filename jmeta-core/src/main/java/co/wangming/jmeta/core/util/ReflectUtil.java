package co.wangming.jmeta.core.util;

import co.wangming.jmeta.core.proxy.Field;
import co.wangming.jmeta.core.proxy.Method;


/**
 * Created By WangMing On 2019/1/26
 **/
public class ReflectUtil {

    public static Field getField(String className, String fieldName) {
        Class clazz = loadClass(className);
        Field field = new Field();
        try {
            field.setField(clazz.getField(fieldName));
        } catch (NoSuchFieldException e) {
            try {
                field.setField(clazz.getDeclaredField(fieldName));
            } catch (NoSuchFieldException e1) {
                Logger.error("{} 找不到字段 {}", className, fieldName, e);
            }
        }
        return field;
    }

    public static Method getMethod(String className, String methodName, Class<?>... parameterTypes) {
        Class clazz = loadClass(className);
        Method method = new Method();
        try {
            method.setMethod(clazz.getMethod(methodName, parameterTypes));
        } catch (NoSuchMethodException e) {
            try {
                method.setMethod(clazz.getDeclaredMethod(methodName, parameterTypes));
            } catch (NoSuchMethodException e1) {
                Logger.error("{} 找不到字段 {}", className, methodName, e);
            }
        }
        return method;
    }

    private static Class loadClass(String className) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        if (classLoader == null) {
            Logger.error("get System class loader is null");
            return null;
        }

        Class<?> clazz = null;
        try {
            clazz = classLoader.loadClass(className);
            Logger.info("loading class({}) result :{}", className, clazz);
        } catch (ClassNotFoundException e) {
            Logger.error("{} 找不到类", className, e);
        }
        return clazz;
    }
}
