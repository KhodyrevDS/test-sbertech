package ru.kds.shoplist.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
        1 * itemTemplateRepository.findById(itemId) >> Optional.of(itemTemplate)
        1 * itemTemplateRepository.delete(itemTemplate)
    }

    def 'should throw ObjectNotFoundException when delete not existed item template'() {
        given:
        Long itemId = 1L

        when:
        itemTemplateService.deleteItemTemplate(itemId)

        then:
        1 * itemTemplateRepository.findById(itemId) >> Optional.empty()

        and:
        thrown ObjectNotFoundException
    }

    def 'should find template items'() {
        given:
        Pageable pageable = new PageRequest(0, 10)
        Page<ItemTemplate> itemTemplates = new PageImpl<>([
                new ItemTemplate([id: 2L, name: 'item 1']),
                new ItemTemplate([id: 3L, name: 'item 2'])
        ])

        when:
        Page<ItemTemplate> result = itemTemplateService.findItemTemplates(pageable)

        then:
        1 * itemTemplateRepository.findAll(pageable) >> itemTemplates

        and:
        result == itemTemplates
    }
}
