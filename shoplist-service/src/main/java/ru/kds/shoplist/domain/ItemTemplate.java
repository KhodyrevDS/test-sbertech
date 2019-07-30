package ru.kds.shoplist.domain;

import java.time.DayOfWeek;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Item template
 */
@Entity
public class ItemTemplate extends AbstractPersistable<Long> {

    private String name;

    private Long price;

    @Enumerated(EnumType.STRING)
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
