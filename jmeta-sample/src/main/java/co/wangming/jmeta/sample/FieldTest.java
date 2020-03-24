package co.wangming.jmeta.sample;

import co.wangming.jmeta.core.annotation.Metadata;

/**
 * Created By WangMing On 2019/1/30
 **/
@Metadata
public class FieldTest {

    private String privateField;

    public String publicField;

    protected String protectedField;

    private static String privateStaticField;

    private static String publicStaticField;

    protected static String protectedStaticField;

}
