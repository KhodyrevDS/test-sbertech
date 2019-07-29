package ru.kds.shoplist.service;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kds.shoplist.domain.Item;
import ru.kds.shoplist.domain.ItemRepository;
import ru.kds.shoplist.domain.Shoplist;
import ru.kds.shoplist.domain.ShoplistRepository;

/**
 * Item service
 */
@Service
public class ItemService {

    private final ShoplistRepository shoplistRepository;

    private final ItemRepository itemRepository;

    public ItemService(ShoplistRepository shoplistRepository, ItemRepository itemRepository) {
        Validate.notNull(shoplistRepository, "parameter shoplistRepository is null");
        Validate.notNull(itemRepository, "parameter itemRepository is null");

        this.shoplistRepository = shoplistRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Create item
     *
     * @param shoplistId the shoplist identifier
     * @param name the item name
     * @param price the item price
     * @return the item
     * @throws ObjectNotFoundException if shoplist or item not exist
     */
    @Transactional
    public Item createItem(Long shoplistId, String name, Long price) throws ObjectNotFoundException {
        Validate.notNull(shoplistId, "parameter shoplistId is null");
        Validate.notBlank(name, "parameter name is null or blank");

        Shoplist shoplist = getShoplist(shoplistId);
        Item item = new Item();
        item.setShoplist(shoplist);
        item.setName(name);
        item.setPrice(price);

        return itemRepository.save(item);
    }

    /**
     * Check or uncheck the item
     *
     * @param shoplistId the shoplist identifier
     * @param id the item identifier
     * @param checked the checked
     * @throws ObjectNotFoundException if shoplist or item not exist
     */
    @Transactional
    public void checkItem(Long shoplistId, Long id, boolean checked) throws ObjectNotFoundException {
        Validate.notNull(shoplistId, "parameter shoplistId is null");
        Validate.notNull(id, "parameter id is null");

        Shoplist shoplist = getShoplist(shoplistId);
        Item item = itemRepository.findByIdAndShoplist_id(id, shoplist.getId()).orElseThrow(
                () -> new ObjectNotFoundException("Item not exist"));
        item.setChecked(checked);

        itemRepository.save(item);
    }

    /**
     * Delete item
     *
     * @param shoplistId the shoplist identifier
     * @param id the item identifier
     * @throws ObjectNotFoundException if shoplist or item not exist
     */
    @Transactional
    public void deleteItem(Long shoplistId, Long id) throws ObjectNotFoundException {
        Validate.notNull(shoplistId, "parameter shoplistId is null");
        Validate.notNull(id, "parameter id is null");

        Shoplist shoplist = getShoplist(shoplistId);
        Item item = itemRepository.findByIdAndShoplist_id(id, shoplist.getId()).orElseThrow(
                () -> new ObjectNotFoundException("Item not exist"));

        itemRepository.delete(item);
    }

    /**
     * Find items
     *
     * @param shoplistId the shoplist identifier
     * @param pageable the page info
     * @return the page of items
     */
    public Page<Item> findItems(Long shoplistId, Pageable pageable) throws ObjectNotFoundException {
        Shoplist shoplist = getShoplist(shoplistId);
        return itemRepository.findByShoplist_id(shoplist.getId(), pageable);
    }

    private Shoplist getShoplist(Long shoplistId) throws ObjectNotFoundException {
        return shoplistRepository.findById(shoplistId).orElseThrow(
                () -> new ObjectNotFoundException("Shoplist not exist"));
    }
}
