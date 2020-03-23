package co.wangming.jmeta.core.classmakers;

import co.wangming.jmeta.core.proxy.Field;
import com.squareup.javapoet.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By WangMing On 2019/1/26
 **/
public class FieldClassMaker extends AbstractClassMaker {

    private static final Logger LOGGER = LogManager.getLogger(FieldClassMaker.class);

    private Map<String, Element> fileds = new HashMap<>();

    public FieldClassMaker(String packageName, Element annotatedElement, Filer filer) {
        super(packageName, annotatedElement, filer);
    }

    public int addField(Element element) {
        fileds.put(element.getSimpleName().toString(), element);
        return fileds.size();
    }

    @Override
    protected String className() {
        return "Fields";
    }

    @Override
    protected void impl(TypeSpec.Builder typeSpec, String targetClassName) {

        // 遍历所有的字段遍历
        for (Element element : fileds.values()) {

            // 这个type应该作为field的泛型参数
            String type = element.asType().toString();

            String fieldName = element.getSimpleName().toString();

            ParameterizedTypeName typeName = ParameterizedTypeName.get(
                    ClassName.get(Field.class),
                    ClassName.bestGuess(type)
            );

            FieldSpec.Builder fieldSpec = FieldSpec.builder(typeName, fieldName)
                    .addModifiers(Modifier.PUBLIC)
                    .addModifiers(Modifier.STATIC);

            String method = ReflectUtilString + ".getField(\"" + targetClassName + "\" , \"" + fieldName + "\");";

            CodeBlock codeBlock = CodeBlock.of(method);
            fieldSpec.initializer(codeBlock);

            typeSpec.addField(fieldSpec.build());
        }
    }
}
