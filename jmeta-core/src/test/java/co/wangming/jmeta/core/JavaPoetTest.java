package co.wangming.jmeta.core;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.junit.Test;

import javax.lang.model.element.Modifier;

/**
 * Created By WangMing On 2019/1/26
 **/
public class JavaPoetTest {

    @Test
    public void main() {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

    }

    @Test
    public void test() {
        ClassName outer = ClassName.get("", "Outer");
        TypeSpec typeSpec = TypeSpec.classBuilder("Outer")
                .addType(TypeSpec.classBuilder("Inner")
                        .addModifiers(Modifier.STATIC)
                        .build())
                .build();

        System.out.println(toString(typeSpec));
    }

    private String toString(TypeSpec typeSpec) {
        return JavaFile.builder("", typeSpec).build().toString();
    }
}
