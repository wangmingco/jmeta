package co.wangming.jmeta.sample.field;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created By WangMing On 2019/1/30
 **/
public class FieldTest {

    @Test
    public void testPrivate() throws IllegalAccessException {
        PrivateField privateField = new PrivateField();
        privateField.setStrValue("test123");

        try {
            String v = PrivateField$Fields.strValue.get(privateField);

            Assert.assertEquals("test123", v);
        } catch (IllegalAccessException e) {
            throw e;
        }
    }

    @Test
    public void testPublic() throws IllegalAccessException {
        PublicField privateField = new PublicField();
        privateField.string = "test123";

        try {
            String v = PublicField$Fields.string.get(privateField);
            Assert.assertEquals("test123", v);

        } catch (IllegalAccessException e) {
            throw e;
        }
    }
}
