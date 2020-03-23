package co.wangming.jmeta.sample.method;

import co.wangming.jmeta.core.annotation.Metadata;

/**
 * Created By WangMing On 2019/1/30
 **/
@Metadata
public class PublicMethod {

    public Long strFuc() {
        return System.currentTimeMillis();
    }
}
