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
import java.util.Set;

/**
 * 创建字段反射类
 * <p>
 * TODO 处理继承下来的字段
 * <p>
 * Created By WangMing On 2020-03-23
 **/
public class FieldsClassMaker extends AbstractClassMaker {

    public FieldsClassMaker(ProcessorContext param) {
        super(param);
    }

    @Override
    protected void createClass() {
        JCTree.JCClassDecl jcClass = processorContext.getJcClass();
        TreeMaker treeMaker = processorContext.getTreeMaker();
        Names names = processorContext.getNames();

        JCTree.JCClassDecl classDef = treeMaker.ClassDef(
                treeMaker.Modifiers(Flags.PUBLIC + Flags.STATIC + Flags.FINAL), //访问标志
                names.fromString("Fields"),   //名字
                List.nil(),         //  泛型形参列表
                null,   //  继承
                List.nil(),         //  接口列表
                createVariables(jcClass, treeMaker, names));  //  定义类

        jcClass.defs = jcClass.defs.append(classDef);
    }

    private List<JCTree> createVariables(JCTree.JCClassDecl jcClass, TreeMaker treeMaker, Names names) {
        Element element = processorContext.getElement();
        Elements elementUtils = processorContext.getElementUtils();

        ListBuffer<JCTree> reflectVariables = new ListBuffer<>();

        List<JCTree.JCVariableDecl> fieldJCVariables = getReflectVariables(jcClass);
        for (JCTree.JCVariableDecl reflectFieldVar : fieldJCVariables) {

            String targetClassName = getClassName(element, elementUtils);

            List<JCTree.JCExpression> params = List.nil();
            params = params.append(treeMaker.Literal(targetClassName));
            params = params.append(treeMaker.Literal(reflectFieldVar.name.toString()));

            JCTree.JCMethodInvocation getFieldInvocation = treeMaker.Apply(List.nil(), memberAccess("ReflectUtil.getField"), params);

            JCTree.JCVariableDecl field = treeMaker.VarDef(
                    treeMaker.Modifiers(Flags.PUBLIC + Flags.STATIC + Flags.FINAL),
                    names.fromString((reflectFieldVar.name.toString())),
                    treeMaker.Ident(names.fromString("Field")),
                    getFieldInvocation
            );

            reflectVariables.append(field);
        }

        return reflectVariables.toList();
    }

    private List<JCTree.JCVariableDecl> getReflectVariables(JCTree.JCClassDecl jcClass) {
        ListBuffer<JCTree.JCVariableDecl> reflectVariableList = new ListBuffer<>();

        for (JCTree jcTree : jcClass.defs) {
            if (!isValidField(jcTree)) {
                continue;
            }
            reflectVariableList.append((JCTree.JCVariableDecl) jcTree);
        }

        return reflectVariableList.toList();
    }

    private static boolean isValidField(JCTree jcTree) {
        if (!jcTree.getKind().equals(JCTree.Kind.VARIABLE)) {
            return false;
        }

        JCTree.JCVariableDecl variable = (JCTree.JCVariableDecl) jcTree;

        Set<Modifier> flagSets = variable.mods.getFlags();


        boolean isStatic = flagSets.contains(Modifier.STATIC);
        boolean isFinal = flagSets.contains(Modifier.FINAL);
        boolean isPublic = flagSets.contains(Modifier.PUBLIC);
        boolean isProtect = flagSets.contains(Modifier.PROTECTED);
        boolean isPrivate = flagSets.contains(Modifier.PRIVATE);

        /**
         * TODO 需要处理不同修饰符的字段映射, 目前是只要是字段就对齐进行映射
         */
        return true;
    }
}
