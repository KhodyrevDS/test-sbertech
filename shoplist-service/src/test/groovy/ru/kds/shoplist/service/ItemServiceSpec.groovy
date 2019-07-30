package ru.kds.shoplist.service

import ru.kds.shoplist.domain.Item
import ru.kds.shoplist.domain.ItemRepository
import ru.kds.shoplist.domain.Shoplist
import ru.kds.shoplist.domain.ShoplistRepository
import spock.lang.Specification

/**
 * Specification for {@link ItemService}
 */
class ItemServiceSpec extends Specification {

    private ShoplistRepository shoplistRepository = Mock(ShoplistRepository)

    private ItemRepository itemRepository = Mock(ItemRepository)

    private ItemService itemService = new ItemService(shoplistRepository, itemRepository)

    def 'should create item'() {
        given:
        Long shoplistId = 1L
        Shoplist shoplist = new Shoplist([id: 1L, name: 'shoplist name'])
        String name = 'item name'
        Long price = 22L

        when:
        Item item = itemService.createItem(shoplistId, name, price)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>(shoplist)
        1 * itemRepository.save(_ as Item) >> { Item itemToSave -> itemToSave }

        and:
        item != null
        item.shoplist == shoplist
        item.name == name
        item.price == price
    }

    def 'should throw ObjectNotFoundException when create item for not existed shoplist'() {
        given:
        Long shoplistId = 1L
        String name = 'item name'
        Long price = 22L

        when:
        itemService.createItem(shoplistId, name, price)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>()

        and:
        thrown ObjectNotFoundException
    }

    def 'should check item'() {
        given:
        Long shoplistId = 1L
        Shoplist shoplist = new Shoplist([id: 1L, name: 'shoplist name'])
        Long itemId = 1L
        boolean checked = true

        Item item = new Item([id: itemId, checked: false])

        when:
        itemService.checkItem(shoplistId, itemId, checked)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>(shoplist)
        1 * itemRepository.findByIdAndShoplist_id(itemId, shoplistId) >> new Optional<>(item)
        1 * itemRepository.save(item) >> { Item itemToSave ->
            assert itemToSave.checked == checked
        }
    }

    def 'should throw ObjectNotFoundException when check item for not existed shoplist'() {
        given:
        Long shoplistId = 1L
        Long itemId = 1L
        boolean checked = true

        when:
        itemService.checkItem(shoplistId, itemId, checked)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>()

        and:
        thrown ObjectNotFoundException
    }

    def 'should throw ObjectNotFoundException when check item for not existed item'() {
        given:
        Long shoplistId = 1L
        Shoplist shoplist = new Shoplist([id: 1L, name: 'shoplist name'])
        Long itemId = 1L
        boolean checked = true

        when:
        itemService.checkItem(shoplistId, itemId, checked)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>(shoplist)
        1 * itemRepository.findByIdAndShoplist_id(itemId, shoplistId) >> new Optional<>()

        and:
        thrown ObjectNotFoundException
    }

    def 'should delete item'() {
        given:
        Long shoplistId = 1L
        Shoplist shoplist = new Shoplist([id: 1L, name: 'shoplist name'])
        Long itemId = 1L
        Item item = new Item([
                id: 1L, name: 'item name', price: 22L
        ])
        when:
        itemService.deleteItem(shoplistId, itemId)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>(shoplist)
        1 * itemRepository.findByIdAndShoplist_id(itemId, shoplistId) >> new Optional<>(item)
        1 * itemRepository.delete(item)
    }

    def 'should throw ObjectNotFoundException when delete for not existed shoplist'() {
        given:
        Long shoplistId = 1L
        Long itemId = 1L

        when:
        itemService.deleteItem(shoplistId, itemId)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>()

        and:
        thrown ObjectNotFoundException
    }

    def 'should throw ObjectNotFoundException when delete not existed item'() {
        given:
        Long shoplistId = 1L
        Shoplist shoplist = new Shoplist([id: 1L, name: 'shoplist name'])
        Long itemId = 1L

        when:
        itemService.deleteItem(shoplistId, itemId)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>(shoplist)
        1 * itemRepository.findByIdAndShoplist_id(itemId, shoplistId) >> new Optional<>()

        and:
        thrown ObjectNotFoundException
    }
}
