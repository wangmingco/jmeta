package co.wangming.jmeta.sample.method;

import co.wangming.jmeta.core.proxy.Method;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created By WangMing On 2019/1/30
 **/
public class MethodTest {

    @Test
    public void testPublic() {

        PublicMethod publicMethod = new PublicMethod();

        Method<Long> strFuc = PublicMethod$Method.strFuc;
        try {
            Long value = strFuc.invoke(publicMethod);
            System.out.println(value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
