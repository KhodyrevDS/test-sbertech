package ru.kds.shoplist.service;

import java.time.DayOfWeek;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kds.shoplist.domain.ItemTemplate;
import ru.kds.shoplist.domain.ItemTemplateRepository;

/**
 * Item template service
 */
@Service
public class ItemTemplateService {

    private final ItemTemplateRepository itemTemplateRepository;

    public ItemTemplateService(ItemTemplateRepository itemTemplateRepository) {
        this.itemTemplateRepository = itemTemplateRepository;
    }

    /**
     * Create item template
     *
     * @param name the item template name
     * @param price the item template price
     * @param dayOfWeek the item template weekday
     * @return the item template
     */
    @Transactional
    public ItemTemplate createItemTemplate(String name, Long price, DayOfWeek dayOfWeek) {
        ItemTemplate itemTemplate = new ItemTemplate();
        itemTemplate.setName(name);
        itemTemplate.setPrice(price);
        itemTemplate.setDayOfWeek(dayOfWeek);

        return itemTemplateRepository.save(itemTemplate);
    }

    /**
     * Delete item template
     *
     * @param id the item template identifier
     * @throws ObjectNotFoundException when item template not exist
     */
    @Transactional
    public void deleteItemTemplate(Long id) throws ObjectNotFoundException {
        ItemTemplate itemTemplate = itemTemplateRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Item template not exist"));

        itemTemplateRepository.delete(itemTemplate);
    }

    /**
     * Find item templates
     *
     * @param pageable page info
     * @return page of item templates
     */
    public Page<ItemTemplate> findItemTemplates(Pageable pageable) {
        return itemTemplateRepository.findAll(pageable);
    }
}
