package ru.kds.shoplist.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Item
 */
@Entity
public class Item extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Shoplist shoplist;

    private String name;

    private Long price;

    private boolean checked = false;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ItemTemplate template;

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

    public ItemTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ItemTemplate template) {
        this.template = template;
    }
}
