package co.wangming.jmeta.core.classmakers;

import co.wangming.jmeta.core.processor.ProcessorContext;
import co.wangming.jmeta.core.proxy.Field;
import co.wangming.jmeta.core.proxy.Method;
import co.wangming.jmeta.core.util.Logger;
import co.wangming.jmeta.core.util.ReflectUtil;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;

/**
 * Created By WangMing On 2020-03-23
 **/
public abstract class AbstractClassMaker {

    protected final ProcessorContext processorContext;

    public AbstractClassMaker(ProcessorContext processorContext) {
        this.processorContext = processorContext;
    }

    public void make() {

        try {
            importPackage(Method.class, Field.class, ReflectUtil.class);
        } catch (Exception e) {
            Logger.error("invoke importPackage() error", e);
            return;
        }

        try {
            createClass();
        } catch (Exception e) {
            Logger.error("invoke createClass() error", e);
        }

    }

    private void importPackage(Class<?>... importClasses) {
        JavacTrees trees = processorContext.getTrees();
        TreeMaker treeMaker = processorContext.getTreeMaker();
        Names names = processorContext.getNames();
        Element element = processorContext.getElement();

        JCTree.JCCompilationUnit compilationUnit = (JCTree.JCCompilationUnit) trees.getPath(element).getCompilationUnit();
        ListBuffer<JCTree> imports = new ListBuffer<>();

        for (Class<?> importClass : importClasses) {

            JCTree.JCIdent ident = treeMaker.Ident(names.fromString(importClass.getPackage().getName()));
            Name name = names.fromString(importClass.getSimpleName());
            JCTree.JCFieldAccess fieldAccess = treeMaker.Select(ident, name);
            JCTree.JCImport jcImport = treeMaker.Import(fieldAccess, false);
            imports.append(jcImport);
        }

        for (int i = 0; i < compilationUnit.defs.size(); i++) {
            imports.append(compilationUnit.defs.get(i));
        }

        compilationUnit.defs = imports.toList();
    }

    protected abstract void createClass();

    protected JCTree.JCExpression memberAccess(String components) {
        TreeMaker treeMaker = processorContext.getTreeMaker();
        Names names = processorContext.getNames();

        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(names.fromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, names.fromString(componentArray[i]));
        }
        return expr;
    }

    protected String getClassName(Element element, Elements elementUtils) {
        String outClassName = element.getSimpleName().toString();

        PackageElement pkg = elementUtils.getPackageOf(element);
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

        String targetClassName = packageName + "." + outClassName;

        Logger.info("targetClassName: {}", targetClassName);
        return targetClassName;
    }
}
