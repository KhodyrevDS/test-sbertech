package ru.kds.shoplist.service;

/**
 * Service exception
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 4969828288010871449L;

    /**
     * Class constructor
     */
    public ServiceException() {
    }

    /**
     * Class constructor
     *
     * @param message the error message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Class constructor
     *
     * @param message the error message
     * @param cause the error cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Class constructor
     *
     * @param cause the error cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
