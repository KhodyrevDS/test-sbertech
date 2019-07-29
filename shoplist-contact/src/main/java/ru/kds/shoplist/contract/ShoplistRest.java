package ru.kds.shoplist.contract;

import java.io.Serializable;

/**
 * Shoplist rest class
 */
public class ShoplistRest implements Serializable {

    private static final long serialVersionUID = 6137701062573062100L;

    private Long id;

    private String name;

    public ShoplistRest() {
    }

    public ShoplistRest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
