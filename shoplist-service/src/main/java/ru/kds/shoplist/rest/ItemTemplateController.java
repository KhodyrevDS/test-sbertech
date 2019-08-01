package ru.kds.shoplist.rest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kds.shoplist.contract.ItemTemplateCreateRequest;
import ru.kds.shoplist.contract.ItemTemplateCreateResponse;
import ru.kds.shoplist.contract.ItemTemplateRest;
import ru.kds.shoplist.contract.PageResponse;
import ru.kds.shoplist.domain.ItemTemplate;
import ru.kds.shoplist.service.ItemTemplateService;
import ru.kds.shoplist.service.ObjectNotFoundException;

/**
 * Item template controller
 */
@RestController
@RequestMapping("/api/v1/item-templates")
public class ItemTemplateController {

    private final ItemTemplateService itemTemplateService;

    public ItemTemplateController(ItemTemplateService itemTemplateService) {
        this.itemTemplateService = itemTemplateService;
    }

    /**
     * Create item template
     *
     * @param request item template create request
     * @return item template create response
     */
    @PostMapping
    public ItemTemplateCreateResponse createItemTemplate(@Valid @RequestBody ItemTemplateCreateRequest request) {
        ItemTemplate itemTemplate = itemTemplateService.createItemTemplate(request.getName(), request.getPrice(),
                request.getDayOfWeek());

        return new ItemTemplateCreateResponse(itemTemplate.getId());
    }

    /**
     * Delete item template
     *
     * @param id item template identificator
     */
    @DeleteMapping("{id}")
    public void deleteItemTemplate(@PathVariable("id") Long id) throws ObjectNotFoundException {
        itemTemplateService.deleteItemTemplate(id);
    }

    @GetMapping
    public PageResponse<ItemTemplateRest> findItemTemplates(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ItemTemplate> itemTemplates = itemTemplateService.findItemTemplates(pageable);
        List<ItemTemplateRest> itemTemplateRestList = new ArrayList<>(itemTemplates.getContent().size());
        for (ItemTemplate itemTemplate : itemTemplates.getContent()) {
            itemTemplateRestList.add(
                    new ItemTemplateRest(itemTemplate.getId(), itemTemplate.getName(), itemTemplate.getPrice(),
                            itemTemplate.getDayOfWeek()));
        }

        return new PageResponse<>(itemTemplateRestList, PageInfoFactory.createPageInfo(itemTemplates));
    }
}
