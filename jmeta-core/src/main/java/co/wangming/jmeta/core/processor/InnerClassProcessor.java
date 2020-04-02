package co.wangming.jmeta.core.processor;

import co.wangming.jmeta.core.annotation.Metadata;
import co.wangming.jmeta.core.classmakers.FieldsClassMaker;
import co.wangming.jmeta.core.classmakers.MethodsClassMaker;
import co.wangming.jmeta.core.util.Logger;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Created By WangMing On 2019/1/25
 **/
@SupportedAnnotationTypes("Metadata")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class InnerClassProcessor extends AbstractProcessor {

    private ProcessorContext processorContext;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        Elements elementUtils = processingEnv.getElementUtils();

        Messager messager = processingEnv.getMessager();
        JavacTrees trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        TreeMaker treeMaker = TreeMaker.instance(context);
        Names names = Names.instance(context);

        this.processorContext = new ProcessorContext(trees, treeMaker, names, messager, elementUtils);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new LinkedHashSet() {{
            add(Metadata.class.getCanonicalName());
        }};
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Logger.setMessager(processorContext.getMessager());

        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(Metadata.class);

        for (Element element : set) {

            JCTree jcTree = processorContext.getTrees().getTree(element);
            if (jcTree == null) {
                Logger.error("查找JCTree失败");
                return false;
            }


            jcTree.accept(new TreeTranslator() {

                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClass) {
                    // 生成字段内部类
                    ProcessorContext param = new ProcessorContext(processorContext, element, jcClass);
                    new FieldsClassMaker(param).make();
                    // 生成方法内部类
                    new MethodsClassMaker(param).make();
                    // TODO 生成注解, 接口, 父类, 泛型 等内部类
                }

            });
        }

        Logger.clean();
        return true;
    }

}
