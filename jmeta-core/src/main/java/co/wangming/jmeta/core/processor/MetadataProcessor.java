package co.wangming.jmeta.core.processor;

import co.wangming.jmeta.core.annotation.Metadata;
import co.wangming.jmeta.core.classmakers.FieldClassMaker;
import co.wangming.jmeta.core.classmakers.MethodClassMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * 该处理器已经被放弃, 使用 #{@link InnerClassProcessor} 作为代替品
 *
 * Created By WangMing On 2019/1/25
 **/
@Deprecated
@SupportedAnnotationTypes("Metadata")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MetadataProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LogManager.getLogger(MetadataProcessor.class);

    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Metadata.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Metadata.class)) {
                makeReflectInnerClass(annotatedElement);
            }
        } catch (Exception e) {
            LOGGER.error("生成类失败", e);
            return false;
        }
        return true;
    }

    private ArrayList<String> makeReflectInnerClass(Element annotatedElement) throws IOException {

        PackageElement pkg = elementUtils.getPackageOf(annotatedElement);
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

        FieldClassMaker fieldClassMaker = new FieldClassMaker(packageName, annotatedElement, filer);
        MethodClassMaker methodClassMaker = new MethodClassMaker(packageName, annotatedElement, filer);

        ArrayList<String> classNames = new ArrayList<>();

        for (Element enclosedElement : annotatedElement.getEnclosedElements()) {
            switch (enclosedElement.getKind()) {
                case FIELD: {
                    fieldClassMaker.addField(enclosedElement);
                    break;
                }
                case METHOD: {
                    methodClassMaker.addMethod(enclosedElement);
                    break;
                }
                case ANNOTATION_TYPE: {
                    break;
                }
                case TYPE_PARAMETER: {
                    break;
                }
                case CONSTRUCTOR: {
                    break;
                }
                case CLASS: {
                    break;
                }
                case ENUM: {
                    break;
                }
                case INTERFACE: {
                    break;
                }
                case PARAMETER: {
                    break;
                }
                case STATIC_INIT: {
                    break;
                }
                case ENUM_CONSTANT: {
                    break;
                }
                case PACKAGE: {
                    break;
                }
                case INSTANCE_INIT: {
                    break;
                }
                case LOCAL_VARIABLE: {
                    break;
                }
                case RESOURCE_VARIABLE: {
                    break;
                }
                case EXCEPTION_PARAMETER: {
                    break;
                }
                case OTHER: {
                    break;
                }
            }
        }

        classNames.add(fieldClassMaker.makeClass());
        classNames.add(methodClassMaker.makeClass());

        return classNames;
    }


}
