# jmeta

在日常Coding中, 如果要写一些底层的框架代码, 经常会用到反射相关的API, 但是说实话, 用起来很难用, 那么能不能让它好用起来呢? 因此便有了这个项目

> 该项目目前还是在开发探索中

我的目标是可以方便快捷的得到类的元信息, 包含方法, 属性, 父类, 接口, 注解等信息.

> TODO 当前重构了一版实现了心心念的内部类实现方式, 但是需要像lombok实现idea插件, 目前还没时间做, 更多的还是在思考, 如何优雅的实现反射吧

## 属性

```java
@Metadata
public class SimpleFields {

    private String privateField;

    public String publicField;

    protected String protectedField;

    private static String privateStaticField;

    private static String publicStaticField;

    protected static String protectedStaticField;

}
```
通过对目标类`SimpleFields`添加`@Metadata` 注解, 可以为`SimpleFields`添加一个`SimpleFields.Fields` 的内部类

```java
public class SimpleFields {
    private String privateField;
    public String publicField;
    protected String protectedField;
    private static String privateStaticField;
    private static String publicStaticField;
    protected static String protectedStaticField;

    public SimpleFields() {
    }

    public static final class Methods {
        public Methods() {
        }
    }

    public static final class Fields {
        public static final Field privateField = ReflectUtil.getField("co.wangming.jmeta.sample.SimpleFields", "privateField");
        public static final Field publicField = ReflectUtil.getField("co.wangming.jmeta.sample.SimpleFields", "publicField");
        public static final Field protectedField = ReflectUtil.getField("co.wangming.jmeta.sample.SimpleFields", "protectedField");
        public static final Field privateStaticField = ReflectUtil.getField("co.wangming.jmeta.sample.SimpleFields", "privateStaticField");
        public static final Field publicStaticField = ReflectUtil.getField("co.wangming.jmeta.sample.SimpleFields", "publicStaticField");
        public static final Field protectedStaticField = ReflectUtil.getField("co.wangming.jmeta.sample.SimpleFields", "protectedStaticField");

        public Fields() {
        }
    }
}

```

然后可以直接使用该内部类访问目标类的元信息
```java
public class SimpleFieldsTest {


    @Test
    public void testPublic() throws IllegalAccessException {
        SimpleFields simpleFields = new SimpleFields();
        simpleFields.publicField = "test123";
        
        try {
            String v = SimpleFields.Fields.publicField.get(simpleFields);
            Assert.assertEquals("test123", v);

        } catch (IllegalAccessException e) {
            throw e;
        }
    }

}
```

> 目前的反射调用还是直接使用JDK的, 在后期优化的时候可以使用基于ASM等的实现, 进行性能优化

## 方法

```java
@Metadata
public class SimpleMethods {

    public void publicVoidFunc() {

    }

    public void publicVoidFunc(String param) {

    }

    public String publicStringFunc() {
        return "";
    }

    public String publicStringFunc(String param) {
        return "";
    }

    public void publicVoidParamFunc(String params) {

    }

    public String publicStringParamFunc(String params) {
        return "";
    }

    //
    public final void publicFinalVoidFunc() {

    }

    public final void publicFinalVoidFunc(String param) {

    }

    public final String publicFinalStringFunc() {
        return "";
    }

    public final String publicFinalStringFunc(String param) {
        return "";
    }

    public final void publicFinalVoidParamFunc(String params) {

    }

    public final String publicFinalStringParamFunc(String params) {
        return "";
    }

    //
    public <T> void publicVoidFunc(T param) {

    }

    public <T> T publicStringFunc(T t) {
        return null;
    }

    public <T> void publicVoidParamFunc(T params) {

    }

    public <T> T publicStringParamFunc(T params) {
        return null;
    }
}
```
对于方法的反射实现就很麻烦了, 因为涉及到了方法重写, 以及泛型, 返回类型等信息, 目前还没想到特别好的方式优雅地解决这些问题
```java
public class SimpleMethods {
    public SimpleMethods() {
    }

    public void publicVoidFunc() {
    }

    public void publicVoidFunc(String param) {
    }

    public String publicStringFunc() {
        return "";
    }

    public String publicStringFunc(String param) {
        return "";
    }

    public void publicVoidParamFunc(String params) {
    }

    public String publicStringParamFunc(String params) {
        return "";
    }

    public final void publicFinalVoidFunc() {
    }

    public final void publicFinalVoidFunc(String param) {
    }

    public final String publicFinalStringFunc() {
        return "";
    }

    public final String publicFinalStringFunc(String param) {
        return "";
    }

    public final void publicFinalVoidParamFunc(String params) {
    }

    public final String publicFinalStringParamFunc(String params) {
        return "";
    }

    public <T> void publicVoidFunc(T param) {
    }

    public <T> T publicStringFunc(T t) {
        return null;
    }

    public <T> void publicVoidParamFunc(T params) {
    }

    public <T> T publicStringParamFunc(T params) {
        return null;
    }

    public static final class Methods {
        public static final Method publicVoidFunc = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicVoidFunc", new Class[0]);
        public static final Method publicVoidFunc_String = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicVoidFunc", new Class[0]);
        public static final Method publicStringFunc = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicStringFunc", new Class[0]);
        public static final Method publicStringFunc_String = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicStringFunc", new Class[0]);
        public static final Method publicVoidParamFunc_String = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicVoidParamFunc", new Class[0]);
        public static final Method publicStringParamFunc_String = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicStringParamFunc", new Class[0]);
        public static final Method publicFinalVoidFunc = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicFinalVoidFunc", new Class[0]);
        public static final Method publicFinalVoidFunc_String = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicFinalVoidFunc", new Class[0]);
        public static final Method publicFinalStringFunc = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicFinalStringFunc", new Class[0]);
        public static final Method publicFinalStringFunc_String = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicFinalStringFunc", new Class[0]);
        public static final Method publicFinalVoidParamFunc_String = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicFinalVoidParamFunc", new Class[0]);
        public static final Method publicFinalStringParamFunc_String = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicFinalStringParamFunc", new Class[0]);
        public static final Method publicVoidFunc_T = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicVoidFunc", new Class[0]);
        public static final Method publicStringFunc_T = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicStringFunc", new Class[0]);
        public static final Method publicVoidParamFunc_T = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicVoidParamFunc", new Class[0]);
        public static final Method publicStringParamFunc_T = ReflectUtil.getMethod("co.wangming.jmeta.sample.SimpleMethods", "publicStringParamFunc", new Class[0]);

        public Methods() {
        }
    }

    public static final class Fields {
        public Fields() {
        }
    }
}

```
