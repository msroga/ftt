package pl.ftt.core.scanner;

public class PackageScannerException extends RuntimeException {
    private static final long serialVersionUID = 547221352637304874L;

    /**
     * Parametrized constructor
     *
     * @param message exception message
     */
    public PackageScannerException(String message) {
        super(message);
    }

    /**
     * Parametrized constructor
     *
     * @param message   message to set
     * @param throwable throwable instance
     */
    public PackageScannerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
