package co.wangming.jmeta.sample;

import co.wangming.jmeta.core.annotation.Metadata;

/**
 * Created By WangMing On 2019/1/30
 **/
@Metadata
public class SimpleMethods {

    public void publicVoidFunc() {

    }

    public void publicVoidFunc(String param) {

    }

    public String publicStringFunc() {
        return "";
    }

    public String publicStringFunc(String param) {
        return "";
    }

    public void publicVoidParamFunc(String params) {

    }

    public String publicStringParamFunc(String params) {
        return "";
    }

    //
    public final void publicFinalVoidFunc() {

    }

    public final void publicFinalVoidFunc(String param) {

    }

    public final String publicFinalStringFunc() {
        return "";
    }

    public final String publicFinalStringFunc(String param) {
        return "";
    }

    public final void publicFinalVoidParamFunc(String params) {

    }

    public final String publicFinalStringParamFunc(String params) {
        return "";
    }

    //
    public <T> void publicVoidFunc(T param) {

    }

    public <T> T publicStringFunc(T t) {
        return null;
    }

    public <T> void publicVoidParamFunc(T params) {

    }

    public <T> T publicStringParamFunc(T params) {
        return null;
    }
}
