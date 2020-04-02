package co.wangming.jmeta.core.plguin;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

/**
 * Created By WangMing On 2019/2/1
 **/
public class JMetaPlugin implements Plugin {

    @Override
    public String getName() {
        return "JMetaPlugin";
    }

    @Override
    public void init(JavacTask task, String... args) {
    }
}
