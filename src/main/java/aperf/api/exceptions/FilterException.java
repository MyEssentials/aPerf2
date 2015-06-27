package aperf.api.exceptions;

import aperf.api.filter.Filter;
import aperf.api.filter.IFilter;

/**
 * Base {@link Exception} thrown by the filter subsystem
 */
public class FilterException extends Exception {
    protected String filterName;

    public FilterException(IFilter filter) {
        super();
        setFilterName(filter);
    }

    public FilterException(IFilter filter, String message) {
        super(message);
        setFilterName(filter);
    }

    public FilterException(IFilter filter, String message, Throwable cause) {
        super(message, cause);
        setFilterName(filter);
    }

    public FilterException(IFilter filter, Throwable cause) {
        super(cause);
        setFilterName(filter);
    }

    @Override
    public String getMessage() {
        return (filterName == null ? "" : "[" + filterName + "] ") + super.getMessage();
    }

    public String getFilterName() {
        return filterName;
    }

    private void setFilterName(IFilter filter) {
        if (filter == null) return;
        Filter annot = filter.getClass().getAnnotation(Filter.class);
        if (annot == null) return;
        filterName = annot.name();
    }

    /**
     * Thrown when an {@link aperf.api.filter.IFilter} fails to load
     */
    public static class FilterLoadException extends FilterException {
        public FilterLoadException(IFilter filter) {
            super(filter);
        }

        public FilterLoadException(IFilter filter, String message) {
            super(filter, message);
        }

        public FilterLoadException(IFilter filter, String message, Throwable cause) {
            super(filter, message, cause);
        }

        public FilterLoadException(IFilter filter, Throwable cause) {
            super(filter, cause);
        }
    }

    /**
     * Thrown when an {@link aperf.api.filter.IFilter} fails to Save
     */
    public static class FilterSaveException extends FilterException {
        public FilterSaveException(IFilter filter) {
            super(filter);
        }

        public FilterSaveException(IFilter filter, String message) {
            super(filter, message);
        }

        public FilterSaveException(IFilter filter, String message, Throwable cause) {
            super(filter, message, cause);
        }

        public FilterSaveException(IFilter filter, Throwable cause) {
            super(filter, cause);
        }
    }

    /**
     * Thrown when an {@link aperf.api.filter.IFilter} can not be found
     */
    public static class FilterNotFoundException extends FilterException {
        public FilterNotFoundException(String filterName) {
            super(null);
            this.filterName = filterName;
        }

        public FilterNotFoundException(String filterName, String message) {
            super(null, message);
            this.filterName = filterName;
        }

        public FilterNotFoundException(String filterName, String message, Throwable cause) {
            super(null, message, cause);
            this.filterName = filterName;
        }

        public FilterNotFoundException(String filterName, Throwable cause) {
            super(null, cause);
            this.filterName = filterName;
        }
    }

    /**
     * Thrown when an {@link aperf.api.filter.IFilter} can not be created
     */
    public static class FilterCreationException extends FilterException {
        public FilterCreationException(String filterName) {
            super(null);
            this.filterName = filterName;
        }

        public FilterCreationException(String filterName, String message) {
            super(null, message);
            this.filterName = filterName;
        }

        public FilterCreationException(String filterName, String message, Throwable cause) {
            super(null, message, cause);
            this.filterName = filterName;
        }

        public FilterCreationException(String filterName, Throwable cause) {
            super(null, cause);
            this.filterName = filterName;
        }
    }
}
