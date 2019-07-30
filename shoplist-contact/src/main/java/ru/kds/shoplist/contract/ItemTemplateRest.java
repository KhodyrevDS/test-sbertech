package ru.kds.shoplist.contract;

import java.io.Serializable;
import java.time.DayOfWeek;

/**
 * Item template rest class
 */
public class ItemTemplateRest implements Serializable {

    private static final long serialVersionUID = 7785014595621118789L;

    private Long id;

    private String name;

    private Long price;

    private DayOfWeek dayOfWeek;

    /**
     * Class constructor
     */
    public ItemTemplateRest() {
    }

    /**
     * Class constructor
     *
     * @param id the item template identifier
     * @param name the item template name
     * @param price the item template price
     * @param dayOfWeek the item template day of week
     */
    public ItemTemplateRest(Long id, String name, Long price, DayOfWeek dayOfWeek) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dayOfWeek = dayOfWeek;
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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
