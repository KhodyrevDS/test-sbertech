package ru.kds.shoplist.service;

/**
 * Service exception
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 4969828288010871449L;

    /**
     * Class constructor
     *
     * @param message the error message
     */
    public ServiceException(String message) {
        super(message);
    }
}
