package ru.kds.shoplist.rest

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import ru.kds.shoplist.contract.ItemCreateRequest
import ru.kds.shoplist.contract.ItemCreateResponse
import ru.kds.shoplist.contract.ItemRest
import ru.kds.shoplist.contract.PageResponse
import ru.kds.shoplist.domain.Item
import ru.kds.shoplist.service.ItemService
import ru.kds.shoplist.service.ObjectNotFoundException
import spock.lang.Specification

/**
 * Specification for {@ItemController}
 */
class ItemControllerSpec extends Specification {

    private ItemService itemService = Mock(ItemService)

    private ItemController itemController = new ItemController(itemService)

    def 'should create item'() {
        given:
        Long shoplistId = 1L
        Long itemId = 2L
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest([name: 'item name', price: 22L])

        when:
        ItemCreateResponse response = itemController.createItem(shoplistId, itemCreateRequest)

        then:
        1 * itemService.createItem(shoplistId, itemCreateRequest.getName(), itemCreateRequest.getPrice()) >>
                new Item(['id': itemId, name: itemCreateRequest.getName(), price: itemCreateRequest.getPrice()])

        and:
        response != null
        response.id == itemId
    }

    def 'should throw ObjectNotFoundException when create item for not existed shoplist'() {
        given:
        Long shoplistId = 1L
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest([name: 'item name', price: 22L])

        when:
        itemController.createItem(shoplistId, itemCreateRequest)

        then:
        1 * itemService.createItem(shoplistId, itemCreateRequest.getName(), itemCreateRequest.getPrice()) >> {
            throw new ObjectNotFoundException('Shoplist not exist')
        }

        and:
        thrown ObjectNotFoundException
    }

    def 'should check item'() {
        given:
        Long shoplistId = 1L
        Long itemId = 2L

        when:
        itemController.checkItem(shoplistId, itemId)

        then:
        1 * itemService.checkItem(shoplistId, itemId, true)
    }

    def 'should throw ObjectNotFoundException when check item for not existed shoplist or item'() {
        given:
        Long shoplistId = 1L
        Long itemId = 2L

        when:
        itemController.checkItem(shoplistId, itemId)

        then:
        1 * itemService.checkItem(shoplistId, itemId, true) >> {
            throw new ObjectNotFoundException('Shoplist not exist')
        }

        and:
        thrown ObjectNotFoundException
    }

    def 'should uncheck item'() {
        given:
        Long shoplistId = 1L
        Long itemId = 2L

        when:
        itemController.uncheckItem(shoplistId, itemId)

        then:
        1 * itemService.checkItem(shoplistId, itemId, false)
    }

    def 'should throw ObjectNotFoundException when uncheck item for not existed shoplist or item'() {
        given:
        Long shoplistId = 1L
        Long itemId = 2L

        when:
        itemController.uncheckItem(shoplistId, itemId)

        then:
        1 * itemService.checkItem(shoplistId, itemId, false) >> {
            throw new ObjectNotFoundException('Shoplist not exist')
        }

        and:
        thrown ObjectNotFoundException
    }

    def 'should delete item'() {
        given:
        Long shoplistId = 1L
        Long itemId = 2L

        when:
        itemController.deleteItem(shoplistId, itemId)

        then:
        1 * itemService.deleteItem(shoplistId, itemId)
    }

    def 'should throw ObjectNotFoundException when delete item for not existed shoplist or item'() {
        given:
        Long shoplistId = 1L
        Long itemId = 2L

        when:
        itemController.deleteItem(shoplistId, itemId)

        then:
        1 * itemService.deleteItem(shoplistId, itemId) >> {
            throw new ObjectNotFoundException('Shoplist not exist')
        }

        and:
        thrown ObjectNotFoundException
    }

    def 'should find items'() {
        given:
        Long shoplistId = 1L
        Long item1Id = 2L
        Long item2Id = 3L
        Long item3Id = 4L

        Pageable pageable = new PageRequest(0, 10)

        when:
        PageResponse<ItemRest> response = itemController.findItems(shoplistId, pageable)

        then:
        1 * itemService.findItems(shoplistId, pageable) >> new PageImpl<>([
                new Item([id: item1Id, name: 'item1', price: 22L]),
                new Item([id: item2Id, name: 'item2']),
                new Item([id: item3Id, name: 'item3', price: 0L])
        ])

        and:
        response.content.size() == 3
        response.content.get(0).id == 2L
        response.content.get(1).id == 3L
        response.content.get(2).id == 4L
    }
}
