package co.wangming.jmeta.core.processor;

import co.wangming.jmeta.core.ElementWrapper;
import co.wangming.jmeta.core.annotation.InnerClass;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created By WangMing On 2019/2/1
 **/
@SupportedAnnotationTypes("InnerClass")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class InnerClassPsrocessor extends AbstractProcessor {

    private static final Logger LOGGER = LogManager.getLogger(MetadataProcessor.class);

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    // JavacTrees提供了待处理的抽象语法树
    private JavacTrees innerClassTrees;
    // TreeMaker封装了创建AST节点的一些方法
    private TreeMaker innerClassTreeMaker;
    // Names提供了创建标识符的方法
    private Names innerClassNames;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();

        this.innerClassTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.innerClassTreeMaker = TreeMaker.instance(context);
        this.innerClassNames = Names.instance(context);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(InnerClass.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            Map<String, ElementWrapper> list = ProcessorUtil.getElementList();
            for (Element innerClassElement : roundEnv.getElementsAnnotatedWith(InnerClass.class)) {
                String name = innerClassElement.getSimpleName().toString();
                String[] nameAyyay = name.split("\\$");
                ElementWrapper outClassElementWrapper = list.get(nameAyyay[0]);

//				ArrayList<String> arrayList = new ArrayList<>();
//				insertReflectInnerClass(outClassElementWrapper, innerClassElement, arrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void insertReflectInnerClass(ElementWrapper outClassElementWrapper, Element innerClassElement, ArrayList<String> arrayList) {

        JCTree outClassJCTree = outClassElementWrapper.getJcTree();

        outClassJCTree.accept(new TreeTranslator() {

            @Override
            public void visitClassDef(JCTree.JCClassDecl outClassJCClassDecl) {

                if (arrayList.contains(outClassJCClassDecl.name.toString())) {
                    return;
                }
                arrayList.add(outClassJCClassDecl.name.toString());

                LOGGER.info("outClassJCClassDecl.name : " + outClassJCClassDecl.name + ", " + outClassJCClassDecl.getSimpleName());
                outClassJCClassDecl.defs = outClassJCClassDecl.defs.append(innerClassTrees.getTree(innerClassElement));

                super.visitClassDef(outClassJCClassDecl);
            }
        });
    }
}
