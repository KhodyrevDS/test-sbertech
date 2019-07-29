package ru.kds.shoplist.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Item
 */
@Entity
public class Item extends AbstractPersistable<Long> {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Shoplist shoplist;

    private String name;

    private Long price;

    private boolean checked = false;

    public Shoplist getShoplist() {
        return shoplist;
    }

    public void setShoplist(Shoplist shoplist) {
        this.shoplist = shoplist;
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
