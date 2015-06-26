package aperf.modules.loader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as an aPerf module
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface APModule {
    /**
     * The name of the Module
     * @return
     */
    String name();

    /**
     * If this Module can be disabled
     * @return
     */
    boolean canDisable() default true;
}
