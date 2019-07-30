package ru.kds.shoplist.contract;

import java.io.Serializable;

/**
 * Item template create response
 */
public class ItemTemplateCreateResponse implements Serializable {

    private static final long serialVersionUID = 5826156834511749402L;

    private Long id;

    /**
     * Class constructor
     */
    public ItemTemplateCreateResponse() {
    }

    /**
     * Class constructor
     *
     * @param id the item identifier
     */
    public ItemTemplateCreateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
