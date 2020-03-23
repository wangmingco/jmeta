package co.wangming.jmeta.core.plguin;

import com.sun.source.util.JavacTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created By WangMing On 2019/2/1
 **/
public class JMetaPlugin implements com.sun.source.util.Plugin {

    private static final Logger LOGGER = LogManager.getLogger(JMetaPlugin.class);

    @Override
    public String getName() {
        return "JMetaPlugin";
    }

    @Override
    public void init(JavacTask task, String... args) {
        LOGGER.info("JMetaPlugin---->  init over, {}, {}", task, args);
    }
}
