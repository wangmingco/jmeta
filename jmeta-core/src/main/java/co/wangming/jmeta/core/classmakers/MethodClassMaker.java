package co.wangming.jmeta.core.classmakers;

import co.wangming.jmeta.core.proxy.Method;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By WangMing On 2019/1/30
 **/
public class MethodClassMaker extends AbstractClassMaker {

    private static final Logger LOGGER = LogManager.getLogger(MethodClassMaker.class);

    private Map<String, Element> methods = new HashMap<>();

    public MethodClassMaker(String packageName, Element annotatedElement, Filer filer) {
        super(packageName, annotatedElement, filer);
    }

    public int addMethod(Element element) {
        methods.put(element.getSimpleName().toString(), element);
        return methods.size();
    }

    @Override
    protected String className() {
        return "Method";
    }

    @Override
    protected void impl(TypeSpec.Builder typeSpec, String targetClassName) {
// 遍历所有的字段遍历
        for (Element element : methods.values()) {

            String fieldName = element.getSimpleName().toString();

            FieldSpec.Builder fieldSpec = FieldSpec.builder(Method.class, fieldName)
                    .addModifiers(Modifier.PUBLIC)
                    .addModifiers(Modifier.STATIC);

            String method = ReflectUtilString + ".getMethod(\"" + targetClassName + "\" , \"" + fieldName + "\");";

            CodeBlock codeBlock = CodeBlock.of(method);
            fieldSpec.initializer(codeBlock);

            typeSpec.addField(fieldSpec.build());
        }
    }
}
