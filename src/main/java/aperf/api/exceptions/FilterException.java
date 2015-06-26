package aperf.api.exceptions;

/**
 * Base {@link Exception} thrown by the filter subsystem
 */
public class FilterException extends Exception {
    public FilterException() {
        super();
    }

    public FilterException(String message) {
        super(message);
    }

    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterException(Throwable cause) {
        super(cause);
    }

    /**
     * Thrown when an {@link aperf.api.filter.IFilter} fails to load
     */
    public static class FilterLoadException extends FilterException {
        public FilterLoadException() {
            super();
        }

        public FilterLoadException(String message) {
            super(message);
        }

        public FilterLoadException(String message, Throwable cause) {
            super(message, cause);
        }

        public FilterLoadException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Thrown when an {@link aperf.api.filter.IFilter} fails to Save
     */
    public static class FilterSaveException extends FilterException {
        public FilterSaveException() {
            super();
        }

        public FilterSaveException(String message) {
            super(message);
        }

        public FilterSaveException(String message, Throwable cause) {
            super(message, cause);
        }

        public FilterSaveException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Thrown when an {@link aperf.api.filter.IFilter} can not be found
     */
    public static class FilterNotFoundException extends FilterException {
        public FilterNotFoundException() {
            super();
        }

        public FilterNotFoundException(String message) {
            super(message);
        }

        public FilterNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        public FilterNotFoundException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Thrown when an {@link aperf.api.filter.IFilter} can not be created
     */
    public static class FilterCreationException extends FilterException {
        public FilterCreationException() {
            super();
        }

        public FilterCreationException(String message) {
            super(message);
        }

        public FilterCreationException(String message, Throwable cause) {
            super(message, cause);
        }

        public FilterCreationException(Throwable cause) {
            super(cause);
        }
    }
}
