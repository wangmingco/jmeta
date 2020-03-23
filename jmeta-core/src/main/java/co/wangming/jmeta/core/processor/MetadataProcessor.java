package co.wangming.jmeta.core.processor;

import co.wangming.jmeta.core.ElementWrapper;
import co.wangming.jmeta.core.annotation.Metadata;
import co.wangming.jmeta.core.handlers.FieldClassMaker;
import co.wangming.jmeta.core.handlers.MethodClassMaker;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Created By WangMing On 2019/1/25
 **/
@SupportedAnnotationTypes("Metadata")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MetadataProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LogManager.getLogger(MetadataProcessor.class);

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;

    // JavacTrees提供了待处理的抽象语法树
    private JavacTrees trees;
    // TreeMaker封装了创建AST节点的一些方法
    private TreeMaker treeMaker;
    // Names提供了创建标识符的方法
    private Names names;


    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();

        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
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
            e.printStackTrace();
        }
        return true;
    }

    private ArrayList<String> makeReflectInnerClass(Element annotatedElement) throws IOException {

        PackageElement pkg = elementUtils.getPackageOf(annotatedElement);
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

        JCTree jcTree = trees.getTree(annotatedElement);

        ElementWrapper elementWrapper = ElementWrapper.build();
        elementWrapper.setAnnotatedElement(annotatedElement);
        elementWrapper.setPackageName(packageName);
        elementWrapper.setFiler(filer);
        elementWrapper.setJcTree(jcTree);

        ProcessorUtil.addElement(elementWrapper);

        FieldClassMaker fieldClassMaker = new FieldClassMaker(elementWrapper);
        MethodClassMaker methodClassMaker = new MethodClassMaker(elementWrapper);

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
