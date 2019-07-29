package ru.kds.shoplist.service

import ru.kds.shoplist.domain.Shoplist
import ru.kds.shoplist.domain.ShoplistRepository
import spock.lang.Specification

/**
 * Specification for {@link ShoplistService}
 */
class ShoplistServiceSpec extends Specification {

    private ShoplistRepository shoplistRepository = Mock(ShoplistRepository)

    private ShoplistService shoplistService = new ShoplistService(shoplistRepository)

    def 'should create shoplist'() {
        given:
        String name = 'shoplist name'

        when:
        Shoplist shoplist = shoplistService.createShoplist(name)

        then:
        1 * shoplistRepository.save(_) >> { Shoplist shoplistToSave -> shoplistToSave }

        and:
        shoplist != null
        shoplist.name == name
    }

    def 'should rename shoplist'() {
        given:
        String newName = 'shoplist newname'
        Long shoplistId = 1L
        Shoplist shoplist = new Shoplist([id: shoplistId, name: 'shoplist oldname'])

        when:
        shoplistService.renameShoplist(shoplistId, newName)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional<>(shoplist)
        1 * shoplistRepository.save(shoplist) >> { Shoplist shoplistToSave ->
            assert shoplistToSave.name == newName
        }
    }

    def 'should throw ObjectNotFoundException when rename not existed shoplist'() {
        given:
        String newName = 'shoplist newname'
        Long shoplistId = 1L

        when:
        shoplistService.renameShoplist(shoplistId, newName)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional()

        and:
        thrown ObjectNotFoundException
    }

    def 'should delete shoplist'() {
        given:
        Long shoplistId = 1L
        Shoplist shoplist = new Shoplist([id: shoplistId, name: 'shoplist oldname'])

        when:
        shoplistService.deleteShoplist(shoplistId)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional(shoplist)
        1 * shoplistRepository.delete(shoplist)
    }

    def 'should throw ObjectNotFoundException when delete not existed shoplist'() {
        given:
        Long shoplistId = 1L

        when:
        shoplistService.deleteShoplist(shoplistId)

        then:
        1 * shoplistRepository.findById(shoplistId) >> new Optional()

        and:
        thrown ObjectNotFoundException
    }
}
