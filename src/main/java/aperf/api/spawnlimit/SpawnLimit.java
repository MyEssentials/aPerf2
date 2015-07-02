package aperf.api.spawnlimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a SpawnLimit.
 * Class MUST extend {@link ISpawnLimit} for registration to work!
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpawnLimit {
    /**
     * The name of the SpawnLimit
     * @return
     */
    String name();

    /**
     * The description of the SpawnLimit
     * @return
     */
    String desc();
}
