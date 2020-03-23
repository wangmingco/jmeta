package co.wangming.jmeta.core.processor;

import co.wangming.jmeta.core.ElementWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By WangMing On 2019/2/1
 **/
public class ProcessorUtil {

    private static final Map<String, ElementWrapper> ELEMENT_LIST = new HashMap<>();

    public static synchronized void addElement(ElementWrapper element) {
        ELEMENT_LIST.put(element.getAnnotatedElement().getSimpleName().toString(), element);
    }

    public static synchronized Map<String, ElementWrapper> getElementList() {
        return ELEMENT_LIST;
    }

}
