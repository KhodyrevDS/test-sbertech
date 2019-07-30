package ru.kds.shoplist.service

import ru.kds.shoplist.domain.ItemTemplate
import ru.kds.shoplist.domain.ItemTemplateRepository
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Specification for {@link ItemTemplateService}
 */
class ItemTemplateServiceSpec extends Specification {

    private ItemTemplateRepository itemTemplateRepository = Mock(ItemTemplateRepository)

    private ItemTemplateService itemTemplateService = new ItemTemplateService(itemTemplateRepository)

    def 'should create item template'() {
        given:
        String name = 'item name'
        Long price = 22L
        DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now())

        when:
        ItemTemplate itemTemplate = itemTemplateService.createItemTemplate(name, price, dayOfWeek)

        then:
        1 * itemTemplateRepository.save(_ as ItemTemplate) >> { ItemTemplate itemTemplateToSave -> itemTemplateToSave }

        and:
        itemTemplate != null
        itemTemplate.name == name
        itemTemplate.price == price
        itemTemplate.dayOfWeek == dayOfWeek
    }

    def 'should delete item template'() {
        given:
        Long itemId = 1L
        ItemTemplate itemTemplate = new ItemTemplate([
                id: 1L, name: 'item name', price: 22L, dayOfWeek: DayOfWeek.FRIDAY
        ])
        when:
        itemTemplateService.deleteItemTemplate(itemId)

        then:
        1 * itemTemplateRepository.findById(itemId) >> new Optional<>(itemTemplate)
        1 * itemTemplateRepository.delete(itemTemplate)
    }

    def 'should throw ObjectNotFoundException when delete not existed item template'() {
        given:
        Long itemId = 1L

        when:
        itemTemplateService.deleteItemTemplate(itemId)

        then:
        1 * itemTemplateRepository.findById(itemId) >> new Optional<>()

        and:
        thrown ObjectNotFoundException
    }
}
