package co.wangming.jmeta.core;

import com.sun.tools.javac.tree.JCTree;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;

/**
 * Created By WangMing On 2019/1/26
 **/
public class ElementWrapper {

    private String packageName;

    private Element annotatedElement;
    private Filer filer;

    private JCTree jcTree;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Filer getFiler() {
        return filer;
    }

    public void setFiler(Filer filer) {
        this.filer = filer;
    }

    public Element getAnnotatedElement() {
        return annotatedElement;
    }

    public void setAnnotatedElement(Element annotatedElement) {
        this.annotatedElement = annotatedElement;
    }

    public static ElementWrapper build() {
        return new ElementWrapper();
    }

    public JCTree getJcTree() {
        return jcTree;
    }

    public void setJcTree(JCTree jcTree) {
        this.jcTree = jcTree;
    }
}
