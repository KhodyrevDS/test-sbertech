package ru.kds.shoplist.rest;

import javax.validation.Valid;
import org.apache.commons.lang3.Validate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kds.shoplist.contract.ShoplistCreateRequest;
import ru.kds.shoplist.contract.ShoplistCreateResponse;
import ru.kds.shoplist.contract.ShoplistRenameRequest;
import ru.kds.shoplist.domain.Shoplist;
import ru.kds.shoplist.service.ObjectNotFoundException;
import ru.kds.shoplist.service.ShoplistService;

/**
 * Shoplist controller
 */
@RestController
@RequestMapping("/api/v1/shoplist")
public class ShoplistController {

    private final ShoplistService shoplistService;

    public ShoplistController(ShoplistService shoplistService) {
        Validate.notNull(shoplistService, "parameter shoplistService is null");
        this.shoplistService = shoplistService;
    }

    /**
     * Create shoplist
     *
     * @param request the shoplist create request
     */
    @PostMapping
    public ShoplistCreateResponse createShoplist(@Valid ShoplistCreateRequest request) {
        Shoplist shoplist = shoplistService.createShoplist(request.getName());
        return new ShoplistCreateResponse(shoplist.getId());
    }

    /**
     * Rename shoplist
     *
     * @param id the shoplist identifier
     * @param request the shoplist rename request
     * @throws ObjectNotFoundException when shoplist not exist
     */
    @PostMapping("{id}")
    public void renameShoplist(@PathVariable Long id, @Valid ShoplistRenameRequest request)
            throws ObjectNotFoundException {
        shoplistService.renameShoplist(id, request.getName());
    }

    /**
     * Delete shoplist
     *
     * @param id the shoplist identifier
     * @throws ObjectNotFoundException when shoplist not exist
     */
    @DeleteMapping("{id}")
    public void deleteShoplist(@PathVariable Long id) throws ObjectNotFoundException {
        shoplistService.deleteShoplist(id);
    }
}
