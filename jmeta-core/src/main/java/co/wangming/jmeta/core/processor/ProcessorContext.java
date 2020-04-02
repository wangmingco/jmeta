package co.wangming.jmeta.core.processor;

import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

/**
 * Created By WangMing On 2020-03-24
 **/
public class ProcessorContext {

    // 语法树
    private final JavacTrees trees;

    // 语法树构造器
    private final TreeMaker treeMaker;

    // 标识符创建器.
    private final Names names;

    // 日志输出器
    private final Messager messager;

    private final Elements elementUtils;

    private final JCTree.JCClassDecl jcClass;

    private final Element element;

    public ProcessorContext(JavacTrees trees, TreeMaker treeMaker, Names names, Messager messager, Elements elementUtils) {
        this.trees = trees;
        this.treeMaker = treeMaker;
        this.names = names;
        this.messager = messager;
        this.elementUtils = elementUtils;
        this.jcClass = null;
        this.element = null;
    }

    public ProcessorContext(ProcessorContext processorContext, Element element, JCTree.JCClassDecl jcClass) {
        this.trees = processorContext.trees;
        this.treeMaker = processorContext.treeMaker;
        this.names = processorContext.names;
        this.messager = processorContext.messager;
        this.elementUtils = processorContext.elementUtils;
        this.jcClass = jcClass;
        this.element = element;
    }

    public JavacTrees getTrees() {
        return trees;
    }

    public TreeMaker getTreeMaker() {
        return treeMaker;
    }

    public Names getNames() {
        return names;
    }

    public Messager getMessager() {
        return messager;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public JCTree.JCClassDecl getJcClass() {
        return jcClass;
    }

    public Element getElement() {
        return element;
    }
}
