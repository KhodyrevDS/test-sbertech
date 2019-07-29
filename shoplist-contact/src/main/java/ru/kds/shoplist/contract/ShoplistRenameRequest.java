package ru.kds.shoplist.contract;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * Shoplist rename request
 */
public class ShoplistRenameRequest implements Serializable {

    private static final long serialVersionUID = 1672024199076916360L;

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
