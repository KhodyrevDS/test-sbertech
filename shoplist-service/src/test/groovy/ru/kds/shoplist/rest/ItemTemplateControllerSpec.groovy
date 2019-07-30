package ru.kds.shoplist.rest

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import ru.kds.shoplist.contract.ItemTemplateCreateRequest
import ru.kds.shoplist.contract.ItemTemplateCreateResponse
import ru.kds.shoplist.contract.ItemTemplateRest
import ru.kds.shoplist.contract.PageResponse
import ru.kds.shoplist.domain.ItemTemplate
import ru.kds.shoplist.service.ItemTemplateService
import ru.kds.shoplist.service.ObjectNotFoundException
import spock.lang.Specification

import java.time.DayOfWeek

/**
 * Specification for {@ItemTemplateController}
 */
class ItemTemplateControllerSpec extends Specification {

    private ItemTemplateService itemTemplateService = Mock(ItemTemplateService)

    private ItemTemplateController itemTemplateController = new ItemTemplateController(itemTemplateService)

    def 'should create item template'() {
        given:
        Long itemId = 1L
        String name = 'item name'
        Long price = 22L
        DayOfWeek dayOfWeek = DayOfWeek.FRIDAY

        ItemTemplateCreateRequest request = new ItemTemplateCreateRequest([name: name, price: price, dayOfWeek: dayOfWeek])

        when:
        ItemTemplateCreateResponse response = itemTemplateController.createItemTemplate(request)

        then:
        1 * itemTemplateService.createItemTemplate(request.getName(), request.getPrice(), request.getDayOfWeek()) >>
                new ItemTemplate(['id': itemId, name: request.getName(), price: request.getPrice()])

        and:
        response != null
        response.id == itemId
    }

    def 'should delete item template'() {
        given:
        Long itemId = 2L

        when:
        itemTemplateController.deleteItemTemplate(itemId)

        then:
        1 * itemTemplateService.deleteItemTemplate(itemId)
    }

    def 'should throw ObjectNotFoundException when delete item for not existed item template'() {
        given:
        Long itemId = 2L

        when:
        itemTemplateController.deleteItemTemplate(itemId)

        then:
        1 * itemTemplateService.deleteItemTemplate(itemId) >> {
            throw new ObjectNotFoundException('Item template not exist')
        }

        and:
        thrown ObjectNotFoundException
    }

    def 'should find item templates'() {
        given:
        Long item1Id = 2L
        Long item2Id = 3L
        Long item3Id = 4L

        Pageable pageable = new PageRequest(0, 10)

        when:
        PageResponse<ItemTemplateRest> response = itemTemplateController.findItemTemplates(pageable)

        then:
        1 * itemTemplateService.findItemTemplates(pageable) >> new PageImpl<>([
                new ItemTemplate([id: item1Id, name: 'item1', price: 22L, dayOfWeek: DayOfWeek.FRIDAY]),
                new ItemTemplate([id: item2Id, name: 'item2', dayOfWeek: DayOfWeek.MONDAY]),
                new ItemTemplate([id: item3Id, name: 'item3', price: 0L, dayOfWeek: DayOfWeek.SUNDAY])
        ])

        and:
        response.content.size() == 3
        response.content.get(0).id == 2L
        response.content.get(1).id == 3L
        response.content.get(2).id == 4L
    }
}
