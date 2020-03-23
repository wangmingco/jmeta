package co.wangming.jmeta.core.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created By WangMing On 2019/1/30
 **/
public class Method<U> {

    private java.lang.reflect.Method method;

    public void setMethod(java.lang.reflect.Method method) {
        this.method = method;
    }

    public Class<?> getDeclaringClass() {
        return method.getDeclaringClass();
    }


    public String getName() {
        return method.getName();
    }


    public int getModifiers() {
        return method.getModifiers();
    }


    public TypeVariable<java.lang.reflect.Method>[] getTypeParameters() {
        return method.getTypeParameters();
    }


    public Class<?> getReturnType() {
        return method.getReturnType();
    }


    public Type getGenericReturnType() {
        return method.getGenericReturnType();
    }


    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }


    public int getParameterCount() {
        return method.getParameterCount();
    }


    public Type[] getGenericParameterTypes() {
        return method.getGenericParameterTypes();
    }


    public Class<?>[] getExceptionTypes() {
        return method.getExceptionTypes();
    }


    public Type[] getGenericExceptionTypes() {
        return method.getGenericExceptionTypes();
    }


    public boolean equals(Object obj) {
        return method.equals(obj);
    }


    public int hashCode() {
        return method.hashCode();
    }


    public String toString() {
        return method.toString();
    }


    public String toGenericString() {
        return method.toGenericString();
    }


    public U invoke(Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return (U) method.invoke(obj, args);
    }


    public boolean isBridge() {
        return method.isBridge();
    }


    public boolean isVarArgs() {
        return method.isVarArgs();
    }


    public boolean isSynthetic() {
        return method.isSynthetic();
    }


    public boolean isDefault() {
        return method.isDefault();
    }


    public Object getDefaultValue() {
        return method.getDefaultValue();
    }


    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return method.getAnnotation(annotationClass);
    }


    public Annotation[] getDeclaredAnnotations() {
        return method.getDeclaredAnnotations();
    }


    public Annotation[][] getParameterAnnotations() {
        return method.getParameterAnnotations();
    }


    public AnnotatedType getAnnotatedReturnType() {
        return method.getAnnotatedReturnType();
    }
}
