package aperf.api.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a Filter.
 * Class MUST extend {@link IFilter} for registration to work!
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {
    /**
     * The name of the Filter
     * @return The name
     */
    String name();

    /**
     * The description of the Filter
     * @return The description
     */
    String desc();

    /**
     * The description of the value type used. (eg. String, Integer, Boolean)
     * @return The value description
     */
    String valueDesc();

    /**
     * If this Filter is also a way to group Objects
     * @return Groups Objects?
     */
    boolean isGrouper() default true;
}
