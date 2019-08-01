package ru.kds.shoplist.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kds.shoplist.domain.ItemRepository;
import ru.kds.shoplist.domain.ItemTemplate;
import ru.kds.shoplist.domain.ItemTemplateRepository;

/**
 * Shedule item creation service
 */
@Service
public class SheduleItemCreationService {

    private ItemTemplateRepository itemTemplateRepository;

    private ItemRepository itemRepository;

    private ItemService itemService;

    public SheduleItemCreationService(ItemTemplateRepository itemTemplateRepository, ItemRepository itemRepository,
            ItemService itemService) {
        this.itemTemplateRepository = itemTemplateRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    /**
     * Create sheduled item
     *
     * @param date the date
     */
    @Transactional
    public void createSheduledItem(LocalDate date) {
        Iterable<ItemTemplate> itemTemplates = itemTemplateRepository.findAll();

        for (ItemTemplate itemTemplate : itemTemplates) {
            if (itemTemplate.getDayOfWeek() == DayOfWeek.from(date) && itemRepository.countActiveSheduledItem(
                    itemTemplate) < 1) {
                itemService.createItem(itemTemplate);
            }
        }
    }
}
