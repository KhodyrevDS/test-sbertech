package ru.kds.shoplist.contract;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * Shoplist create request
 */
public class ShoplistCreateRequest implements Serializable {

    private static final long serialVersionUID = 7341823412031921307L;

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
