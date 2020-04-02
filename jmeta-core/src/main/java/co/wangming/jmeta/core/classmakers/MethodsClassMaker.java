package co.wangming.jmeta.core.classmakers;

import co.wangming.jmeta.core.processor.ProcessorContext;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 创建方法反射类
 * <p>
 * TODO 处理继承下来的方法
 * <p>
 * Created By WangMing On 2020-03-23
 **/
public class MethodsClassMaker extends AbstractClassMaker {

    public MethodsClassMaker(ProcessorContext processorContext) {
        super(processorContext);
    }

    @Override
    protected void createClass() {
        JCTree.JCClassDecl jcClass = processorContext.getJcClass();
        TreeMaker treeMaker = processorContext.getTreeMaker();
        Names names = processorContext.getNames();

        JCTree.JCClassDecl classDef = treeMaker.ClassDef(
                treeMaker.Modifiers(Flags.PUBLIC + Flags.STATIC + Flags.FINAL), //访问标志
                names.fromString("Methods"),   //名字
                List.nil(),         //  泛型形参列表
                null,   //  继承
                List.nil(),         //  接口列表
                createMethods(jcClass, treeMaker, names));  //  定义类

        jcClass.defs = jcClass.defs.append(classDef);
    }

    private List<JCTree> createMethods(JCTree.JCClassDecl jcClass, TreeMaker treeMaker, Names names) {
        Element element = processorContext.getElement();
        Elements elementUtils = processorContext.getElementUtils();

        ListBuffer<JCTree> varList = new ListBuffer<>();

        for (JCTree.JCMethodDecl targetMethod : getMethods(jcClass)) {

            if ("<init>".equals(targetMethod.name.toString())) {
                continue;
            }
            // TODO 泛型信息处理, 虽然泛型信息不参与签名, 但是为了可读性是否要在方法中提供泛型信息?

            java.util.List<String> list = new ArrayList();
            for (JCTree.JCVariableDecl parameter : targetMethod.getParameters()) {
                list.add(parameter.vartype.toString());
            }

            String methodName = targetMethod.name.toString();
            if (list.size() > 0) {
                methodName += "_" + list.stream().collect(Collectors.joining("_"));
            }

            String targetClassName = getClassName(element, elementUtils);

            List<JCTree.JCExpression> params = List.nil();
            params = params.append(treeMaker.Literal(targetClassName));
            params = params.append(treeMaker.Literal(targetMethod.name.toString()));

            JCTree.JCMethodInvocation getMethodInvocation = treeMaker.Apply(List.nil(), memberAccess("ReflectUtil.getMethod"), params);

            JCTree.JCVariableDecl field = treeMaker.VarDef(
                    treeMaker.Modifiers(Flags.PUBLIC + Flags.STATIC + Flags.FINAL),
                    names.fromString(methodName),
                    treeMaker.Ident(names.fromString("Method")),
                    getMethodInvocation
            );

            varList.append(field);
        }

        return varList.toList();
    }

    private List<JCTree.JCMethodDecl> getMethods(JCTree.JCClassDecl jcClass) {
        ListBuffer<JCTree.JCMethodDecl> jcVariables = new ListBuffer<>();

        for (JCTree jcTree : jcClass.defs) {
            if (!jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                continue;
            }

            JCTree.JCMethodDecl jcVariable = (JCTree.JCMethodDecl) jcTree;
            Set<Modifier> flagSets = jcVariable.mods.getFlags();

            // TODO
            boolean isStatic = flagSets.contains(Modifier.STATIC);
            boolean isFinal = flagSets.contains(Modifier.FINAL);
            boolean isPublic = flagSets.contains(Modifier.PUBLIC);
            boolean isProtect = flagSets.contains(Modifier.PROTECTED);
            boolean isPrivate = flagSets.contains(Modifier.PRIVATE);

            jcVariables.append((JCTree.JCMethodDecl) jcTree);
        }

        return jcVariables.toList();
    }


}
