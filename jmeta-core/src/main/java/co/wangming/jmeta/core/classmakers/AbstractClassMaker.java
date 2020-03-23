package co.wangming.jmeta.core.classmakers;

import co.wangming.jmeta.core.ReflectUtil;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import java.io.IOException;

/**
 * Created By WangMing On 2019/1/30
 **/
public abstract class AbstractClassMaker {

    private static final Logger LOGGER = LogManager.getLogger(FieldClassMaker.class);

    protected static final String ReflectUtilString = ReflectUtil.class.getCanonicalName();

    private final String packageName;
    private final Element annotatedElement;
    private final Filer filer;


    public AbstractClassMaker(String packageName, Element annotatedElement, Filer filer) {
        this.packageName = packageName;
        this.annotatedElement = annotatedElement;
        this.filer = filer;
    }

    public String makeClass() throws IOException {
        String outClassName = annotatedElement.getSimpleName().toString();

        String targetClassName = packageName + "." + outClassName;
        String innerClassName = outClassName + "$" + className();

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(innerClassName);

        impl(classBuilder, targetClassName);

        // Write file
        JavaFile javaFile = JavaFile.builder(packageName, classBuilder.build()).build();

        javaFile.writeTo(filer);
        return innerClassName;
    }

    protected abstract String className();

    protected abstract void impl(TypeSpec.Builder typeSpec, String targetClassName);
}
