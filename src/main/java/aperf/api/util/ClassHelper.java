package aperf.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassHelper {
    public static List<Class<?>> getAllInterfaces(Class<?> cls) {
        List<Class<?>> lst = new ArrayList<Class<?>>();

        lst.addAll(Arrays.asList(cls.getInterfaces()));

        Class<?> s = cls.getSuperclass();
        if (s != null) {
            lst.addAll(getAllInterfaces(s));
        }

        return lst;
    }
}
