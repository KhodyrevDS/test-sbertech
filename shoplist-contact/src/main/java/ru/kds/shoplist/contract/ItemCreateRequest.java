package ru.kds.shoplist.contract;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * Item create request
 */
public class ItemCreateRequest implements Serializable {

    private static final long serialVersionUID = -322881422028986188L;

    @NotBlank
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
