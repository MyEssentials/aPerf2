package aperf.util.context;

// Based on https://github.com/nallar/TickProfiler/blob/master/src/main/java/nallar/tickprofiler/util/contextaccess/ContextAccess.java

public interface ContextAccess {
    public static final ContextAccess $ = ContextAccessProvider.getContextAccess();

    Class getContext(int depth);
}
