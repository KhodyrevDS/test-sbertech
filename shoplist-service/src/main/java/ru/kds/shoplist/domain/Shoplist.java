package ru.kds.shoplist.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Shoplist
 */
@Entity
public class Shoplist extends AbstractPersistable<Long> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
