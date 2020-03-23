package co.wangming.jmeta.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created By WangMing On 2019/1/25
 **/
@Target({TYPE, METHOD, CONSTRUCTOR, FIELD, ANNOTATION_TYPE})
@Retention(SOURCE)
public @interface Metadata {
}
