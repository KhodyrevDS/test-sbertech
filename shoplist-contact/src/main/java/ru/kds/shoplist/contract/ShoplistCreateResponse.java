package ru.kds.shoplist.contract;

import java.io.Serializable;

public class ShoplistCreateResponse implements Serializable {

    private static final long serialVersionUID = 4815828056231472760L;

    private Long id;

    /**
     * Class constructor
     */
    public ShoplistCreateResponse() {
    }

    /**
     * Class constructor
     *
     * @param id the shoplist identifier
     */
    public ShoplistCreateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
