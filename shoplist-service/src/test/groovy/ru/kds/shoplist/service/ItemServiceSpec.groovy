package ru.kds.shoplist.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import ru.kds.shoplist.domain.Item
import ru.kds.shoplist.domain.ItemRepository
import ru.kds.shoplist.domain.ItemTemplate
import ru.kds.shoplist.domain.Shoplist
import ru.kds.shoplist.domain.ShoplistRepository
import spock.lang.Specification

import java.time.DayOfWeek

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
        1 * shoplistRepository.findById(shoplistId) >> Optional.of(shoplist)
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
        1 * shoplistRepository.findById(shoplistId) >> Optional.empty()

        and:
        thrown ObjectNotFoundException
    }

    def 'should create item from template'() {
        given:
        ItemTemplate template = new ItemTemplate([id: 1L, name: 'item name', price: 22L, dayOfWeek: DayOfWeek.SUNDAY])

        when:
        Item item = itemService.createItem(template)

        then:
        1 * itemRepository.save(_ as Item) >> { Item itemToSave -> itemToSave }
        item.name == template.name
        item.price == template.price
        !item.checked
        item.template == template
        item.shoplist == null
    }

    def 'should check item'() {
        given:
        Long itemId = 1L
        boolean checked = true

        Item item = new Item([id: itemId, checked: false])

        when:
        itemService.checkItem(itemId, checked)

        then:
        1 * itemRepository.findById(itemId) >> Optional.of(item)
        1 * itemRepository.save(item) >> { Item itemToSave ->
            assert itemToSave.checked == checked
        }
    }

    def 'should throw ObjectNotFoundException when check item for not existed item'() {
        given:
        Long itemId = 1L
        boolean checked = true

        when:
        itemService.checkItem(itemId, checked)

        then:
        1 * itemRepository.findById(itemId) >> Optional.empty()

        and:
        thrown ObjectNotFoundException
    }

    def 'should delete item'() {
        given:
        Long itemId = 1L
        Item item = new Item([
                id: 1L, name: 'item name', price: 22L
        ])
        when:
        itemService.deleteItem(itemId)

        then:
        1 * itemRepository.findById(itemId) >> Optional.of(item)
        1 * itemRepository.delete(item)
    }

    def 'should throw ObjectNotFoundException when delete not existed item'() {
        given:
        Long itemId = 1L

        when:
        itemService.deleteItem(itemId)

        then:
        1 * itemRepository.findById(itemId) >> Optional.empty()

        and:
        thrown ObjectNotFoundException
    }

    def 'should find shoplist items'() {
        given:
        Long shoplistId = 1L
        Shoplist shoplist = new Shoplist([id: 1L, name: 'shoplist name'])
        Pageable pageable = new PageRequest(0, 10)
        Page<Item> items = new PageImpl<>([
                new Item([id: 2L, name: 'item 1', shoplist: shoplist]),
                new Item([id: 3L, name: 'item 2', shoplist: shoplist])
        ])

        when:
        Page<Item> result = itemService.findShoplistItems(shoplistId, pageable)

        then:
        1 * shoplistRepository.findById(shoplistId) >> Optional.of(shoplist)
        1 * itemRepository.findByShoplist_id(shoplist.getId(), pageable) >> items

        and:
        result == items
    }

    def 'should throw ObjectNotFoundException when find items for not existed shoplist'() {
        given:
        Long shoplistId = 1L

        when:
        itemService.findShoplistItems(shoplistId, new PageRequest(0, 10))

        then:
        1 * shoplistRepository.findById(shoplistId) >> Optional.empty()

        and:
        thrown ObjectNotFoundException
    }

    def 'should find unchecked items'() {
        given:
        Pageable pageable = new PageRequest(0, 10)
        Page<Item> items = new PageImpl<>([
                new Item([id: 2L, name: 'item 1']),
                new Item([id: 3L, name: 'item 2'])
        ])

        when:
        Page<Item> result = itemService.findUncheckedItems(pageable)

        then:
        1 * itemRepository.findByCheckedFalse(pageable) >> items

        and:
        result == items
    }
}
