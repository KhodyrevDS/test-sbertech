package ru.kds.shoplist.contract;

import java.io.Serializable;

/**
 * Item rest
 */
public class ItemRest implements Serializable {

    private static final long serialVersionUID = -5842810092924597725L;

    private Long id;

    private String name;

    private Long price;

    private boolean checked;

    /**
     * Class constructor
     */
    public ItemRest() {
    }

    /**
     * Class constructor
     *
     * @param id the item identifier
     * @param name the item name
     * @param price the item price
     * @param checked is item checked
     */
    public ItemRest(Long id, String name, Long price, boolean checked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.checked = checked;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
