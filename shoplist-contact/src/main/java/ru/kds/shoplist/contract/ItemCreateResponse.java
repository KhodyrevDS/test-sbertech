package ru.kds.shoplist.contract;

import java.io.Serializable;

/**
 * Item create response
 */
public class ItemCreateResponse implements Serializable {

    private static final long serialVersionUID = 5826156834511749402L;

    private Long id;

    /**
     * Class constructor
     */
    public ItemCreateResponse() {
    }

    /**
     * Class constructor
     *
     * @param id the item identifier
     */
    public ItemCreateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
