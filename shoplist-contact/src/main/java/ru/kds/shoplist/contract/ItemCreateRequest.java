package ru.kds.shoplist.contract;

import java.io.Serializable;

/**
 * Item create request
 */
public class ItemCreateRequest implements Serializable {

    private static final long serialVersionUID = -322881422028986188L;

    private String name;

    private Long price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
