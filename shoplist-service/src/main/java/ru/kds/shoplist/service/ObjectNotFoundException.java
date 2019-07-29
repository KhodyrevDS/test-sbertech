package ru.kds.shoplist.service;

/**
 * Object not found exception
 */
public class ObjectNotFoundException extends ServiceException {

    private static final long serialVersionUID = -4157211764210267701L;

    /**
     * Class constructor
     *
     * @param message the error message
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
