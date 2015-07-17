package aperf.api.grouper;

import java.util.Comparator;
import java.util.Map;

public class DefaultGrouperComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> arg0, Map.Entry<String, Integer> arg1) {
        return Integer.compare(arg1.getValue(), arg0.getValue());
    }
}
