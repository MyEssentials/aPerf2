package aperf.util.context;

// Based on https://github.com/nallar/TickProfiler/blob/master/src/main/java/nallar/tickprofiler/util/contextaccess/ContextAccessSecurityManager.java

public class ContextAccessSecurityManager extends SecurityManager implements ContextAccess {
    @Override
    public Class getContext(int depth) {
        return getClassContext()[depth + 1];
    }
}
