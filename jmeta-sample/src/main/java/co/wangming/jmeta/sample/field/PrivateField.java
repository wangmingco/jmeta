package co.wangming.jmeta.sample.field;

import co.wangming.jmeta.core.annotation.Metadata;

/**
 * Created By WangMing On 2019/1/30
 **/
@Metadata
public class PrivateField {

    public String strValue;

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }
}
