package ru.kds.shoplist.contract;

import java.io.Serializable;
import java.time.DayOfWeek;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Item template create request
 */
public class ItemTemplateCreateRequest implements Serializable {

    private static final long serialVersionUID = 8719687970147423117L;

    @NotBlank
    private String name;

    private Long price;

    @NotNull
    private DayOfWeek dayOfWeek;

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
