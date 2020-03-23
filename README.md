# jmeta

jmeta是一款在编译期就能得知类信息的框架. 

> 该框架不适合在运行期动态生成类的场景

## 版本更迭
#### 原版

```java
public class Test {
     public static void main(String[] args) throws NoSuchFieldException {
            // 原始的
            Field stringField1 = SimpleObject.class.getField("string");
            // 快捷的
            Field stringFiled2 = SimpleObjectMetadata.string;
            // idea 插件, 这段代码怎么替换到java源码里面去, 需要做源码替换了, 参考lombok 是怎么做set/get生成的
            Field stringFiled3 = SimpleObject.metadata.string;
        }
    
        public class SimpleObject {
            private String string;
            public String getString() {
                return string;
            }
            public void setString(String string) {
                this.string = string;
            }
        }
    
        运行期 这个类可以由javasist动态生成
        public static class SimpleObjectMetadata {
            public static Field string;
            public static Method getString;
            public static Method setString;
        }
}
```
   

#### 优化版
在idea里, 在一个类后面点击点, 就会出现提示, Fileds, methods等. 参考lombok的get/set方法生成.
重构改完一个类名之后, 这里也要修改.

```java
public class Test {
    public static void main(String[] args) throws NoSuchFieldException {
            // 原始的
            Field stringField1 = SimpleObject.class.getField("string");
            Field stringFiled3 = SimpleObject.Fields.string;
    
            // 利用javac annotation processing 把SimpleObject.class.getField("string"); 替换成 SimpleObject.Fields.string;
        }
    
        public class SimpleObject {
            private String string;
            public String getString() {
                return string;
            }
            public void setString(String string) {
                this.string = string;
            }
    
             // 运行期 这个类可以由javasist动态生成
             public static class Methods {
                    public static Method getString = SimpleObject.class.getMethod("getString");
                    public static Method setString = SimpleObject.class.getMethod("getString");
             }
    
             public static class Fields {
                     public static Field string = SimpleObject.class.getField("string");
             }
        }

}
```
    
