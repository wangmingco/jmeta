package co.wangming.jmeta.core.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

/**
 * Created By WangMing On 2019/1/29
 **/
public class Field<U> {

    private java.lang.reflect.Field field;

    public void setField(java.lang.reflect.Field field) {
        this.field = field;
    }

    public Class<?> getDeclaringClass() {
        return field.getDeclaringClass();
    }


    public String getName() {
        return field.getName();
    }


    public int getModifiers() {
        return field.getModifiers();
    }


    public boolean isEnumConstant() {
        return field.isEnumConstant();
    }


    public boolean isSynthetic() {
        return field.isSynthetic();
    }


    public Class<?> getType() {
        return field.getType();
    }


    public Type getGenericType() {
        return field.getGenericType();
    }


    public boolean equals(Object obj) {
        return field.equals(obj);
    }


    public int hashCode() {
        return field.hashCode();
    }


    public String toString() {
        return field.toString();
    }


    public String toGenericString() {
        return field.toGenericString();
    }


    public U get(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return (U) field.get(obj);
    }


    public boolean getBoolean(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return field.getBoolean(obj);
    }


    public byte getByte(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return field.getByte(obj);
    }


    public char getChar(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return field.getChar(obj);
    }


    public short getShort(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return field.getShort(obj);
    }


    public int getInt(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return field.getInt(obj);
    }


    public long getLong(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return field.getLong(obj);
    }


    public float getFloat(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return field.getFloat(obj);
    }


    public double getDouble(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return field.getDouble(obj);
    }


    public void set(Object obj, Object value) throws IllegalArgumentException, IllegalAccessException {
        field.set(obj, value);
    }


    public void setBoolean(Object obj, boolean z) throws IllegalArgumentException, IllegalAccessException {
        field.setBoolean(obj, z);
    }


    public void setByte(Object obj, byte b) throws IllegalArgumentException, IllegalAccessException {
        field.setByte(obj, b);
    }


    public void setChar(Object obj, char c) throws IllegalArgumentException, IllegalAccessException {
        field.setChar(obj, c);
    }


    public void setShort(Object obj, short s) throws IllegalArgumentException, IllegalAccessException {
        field.setShort(obj, s);
    }


    public void setInt(Object obj, int i) throws IllegalArgumentException, IllegalAccessException {
        field.setInt(obj, i);
    }


    public void setLong(Object obj, long l) throws IllegalArgumentException, IllegalAccessException {
        field.setLong(obj, l);
    }


    public void setFloat(Object obj, float f) throws IllegalArgumentException, IllegalAccessException {
        field.setFloat(obj, f);
    }


    public void setDouble(Object obj, double d) throws IllegalArgumentException, IllegalAccessException {
        field.setDouble(obj, d);
    }


    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return field.getAnnotation(annotationClass);
    }


    public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
        return field.getAnnotationsByType(annotationClass);
    }


    public Annotation[] getDeclaredAnnotations() {
        return field.getDeclaredAnnotations();
    }


    public AnnotatedType getAnnotatedType() {
        return field.getAnnotatedType();
    }
}
