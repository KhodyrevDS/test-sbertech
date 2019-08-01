package ru.kds.shoplist.service

import ru.kds.shoplist.domain.ItemRepository
import ru.kds.shoplist.domain.ItemTemplate
import ru.kds.shoplist.domain.ItemTemplateRepository
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Specification for {@link SheduleItemCreationService}
 */
class SheduleItemCreationServiceSpec extends Specification {

    private ItemTemplateRepository itemTemplateRepository = Mock(ItemTemplateRepository)

    private ItemRepository itemRepository = Mock(ItemRepository)

    private ItemService itemService = Mock(ItemService)

    private SheduleItemCreationService sheduleItemCreationService =
            new SheduleItemCreationService(itemTemplateRepository, itemRepository, itemService)

    def 'should create sheduled items'() {
        given:
        LocalDate date = LocalDate.now()
        List<ItemTemplate> itemTemplates = [
                new ItemTemplate([id: 1L, name: 'template1', price: 22L, dayOfWeek: DayOfWeek.from(date)]),
                new ItemTemplate([id: 2L, name: 'template2', dayOfWeek: DayOfWeek.from(date.plusDays(1))]),
                new ItemTemplate([id: 3L, name: 'template3', dayOfWeek: DayOfWeek.from(date.minusDays(1))])
        ]

        when:
        sheduleItemCreationService.createSheduledItem(date)

        then:
        1 * itemTemplateRepository.findAll() >> itemTemplates
        1 * itemRepository.countActiveSheduledItem(_) >> count
        n * itemService.createItem(itemTemplates[0])

        where:
        n | count
        1 | 0
        0 | 1
    }
}
