package ru.kds.shoplist.rest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kds.shoplist.contract.ItemCreateRequest;
import ru.kds.shoplist.contract.ItemCreateResponse;
import ru.kds.shoplist.contract.ItemRest;
import ru.kds.shoplist.contract.PageResponse;
import ru.kds.shoplist.domain.Item;
import ru.kds.shoplist.service.ItemService;
import ru.kds.shoplist.service.ObjectNotFoundException;

/**
 * Item controller
 */
@RestController
@RequestMapping("/api/v1")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        Validate.notNull(itemService, "parameter itemService is null");

        this.itemService = itemService;
    }

    /**
     * Create item
     *
     * @param shoplistId the shoplist identifier
     * @param request item create request
     * @return item create response
     * @throws ObjectNotFoundException when shoplist not exist
     */
    @PostMapping("/shoplist/{shoplistId}/items")
    public ItemCreateResponse createItem(@PathVariable("shoplistId") Long shoplistId,
            @Valid @RequestBody ItemCreateRequest request) throws ObjectNotFoundException {
        Item item = itemService.createItem(shoplistId, request.getName(), request.getPrice());

        return new ItemCreateResponse(item.getId());
    }

    /**
     * Find items
     *
     * @param shoplistId the shoplist identifier
     * @throws ObjectNotFoundException when shoplist or item not exist
     */
    @GetMapping("/shoplist/{shoplistId}/items")
    public PageResponse<ItemRest> findItems(@PathVariable("shoplistId") Long shoplistId, Pageable pageable)
            throws ObjectNotFoundException {
        Page<Item> items = itemService.findShoplistItems(shoplistId, pageable);
        List<ItemRest> itemRestList = new ArrayList<>(items.getContent().size());
        for (Item item : items.getContent()) {
            itemRestList.add(new ItemRest(item.getId(), item.getName(), item.getPrice(), item.isChecked()));
        }

        return new PageResponse<>(itemRestList, PageInfoFactory.createPageInfo(items));
    }

    /**
     * Find unchecked items
     */
    @GetMapping("/items")
    public PageResponse<ItemRest> findUncheckedItems(Pageable pageable) {
        Page<Item> items = itemService.findUncheckedItems(pageable);
        List<ItemRest> itemRestList = new ArrayList<>(items.getContent().size());
        for (Item item : items.getContent()) {
            itemRestList.add(new ItemRest(item.getId(), item.getName(), item.getPrice(), item.isChecked()));
        }

        return new PageResponse<>(itemRestList, PageInfoFactory.createPageInfo(items));
    }

    /**
     * Check item
     *
     * @param id the item identifier
     * @throws ObjectNotFoundException when shoplist or item not exist
     */
    @PostMapping("/items/{id}/check")
    public void checkItem(@PathVariable("id") Long id) throws ObjectNotFoundException {
        itemService.checkItem(id, true);
    }

    /**
     * Uncheck item
     *
     * @param id the item identifier
     * @throws ObjectNotFoundException when shoplist or item not exist
     */
    @PostMapping("/items/{id}/uncheck")
    public void uncheckItem(@PathVariable("id") Long id) throws ObjectNotFoundException {
        itemService.checkItem(id, false);
    }

    /**
     * Delete item
     *
     * @param id the item identifier
     * @throws ObjectNotFoundException when shoplist or item not exist
     */
    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable("id") Long id) throws ObjectNotFoundException {
        itemService.deleteItem(id);
    }
}
