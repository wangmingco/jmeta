package co.wangming.jmeta.core.handlers;

import co.wangming.jmeta.core.ElementWrapper;
import co.wangming.jmeta.core.ReflectUtil;
import co.wangming.jmeta.core.annotation.InnerClass;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.lang.model.element.Element;
import java.io.IOException;

/**
 * Created By WangMing On 2019/1/30
 **/
public abstract class AbstractClassMaker {

    private static final Logger LOGGER = LogManager.getLogger(FieldClassMaker.class);

    protected static final String ReflectUtilString = ReflectUtil.class.getCanonicalName();

    protected ElementWrapper elementWrapper;

    protected AbstractClassMaker(ElementWrapper elementWrapper) {
        this.elementWrapper = elementWrapper;
    }

    public String makeClass() throws IOException {
        Element annotatedElement = elementWrapper.getAnnotatedElement();
        String outClassName = annotatedElement.getSimpleName().toString();

        /**
         * 	TODO 因为内部类里没有外部类信息的, 所以$符号不能进行内部类连接, 使用Javapoet也无法添加内部类
         * 	只能在外部类里的  classes[] class文件里把内部类添加进去, 这个就超出了annotated processing 的处理范围了
         * 	目前只能通过Out$Fields来进行处理了.
         */

        String targetClassName = elementWrapper.getPackageName() + "." + outClassName;
        String innerClassName = outClassName + "$" + className();

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(innerClassName).addAnnotation(InnerClass.class);

        impl(elementWrapper, classBuilder, targetClassName);

        // Write file
        JavaFile javaFile = JavaFile.builder(elementWrapper.getPackageName(), classBuilder.build()).build();

        javaFile.writeTo(elementWrapper.getFiler());
        return innerClassName;
    }

    protected abstract String className();

    protected abstract void impl(ElementWrapper elementWrapper, TypeSpec.Builder typeSpec, String targetClassName);
}
