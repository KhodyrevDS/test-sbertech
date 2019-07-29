package ru.kds.shoplist.rest

import ru.kds.shoplist.contract.ShoplistCreateRequest
import ru.kds.shoplist.contract.ShoplistCreateResponse
import ru.kds.shoplist.contract.ShoplistRenameRequest
import ru.kds.shoplist.domain.Shoplist
import ru.kds.shoplist.service.ObjectNotFoundException
import ru.kds.shoplist.service.ShoplistService
import spock.lang.Specification

/**
 * Specification for {@link ShoplistController}
 */
class ShoplistControllerSpec extends Specification {

    private ShoplistService shoplistService = Mock(ShoplistService)

    private ShoplistController shoplistController = new ShoplistController(shoplistService)

    def 'should create shoplist'() {
        given:
        ShoplistCreateRequest request = new ShoplistCreateRequest([name: 'shoplist name'])

        Long shoplistId = 1L

        when:
        ShoplistCreateResponse response = shoplistController.createShoplist(request)

        then:
        1 * shoplistService.createShoplist(request.getName()) >> new Shoplist([id: shoplistId, name: request.getName()])

        and:
        response != null
        response.id == shoplistId
    }

    def 'should rename shoplist'() {
        given:
        ShoplistRenameRequest request = new ShoplistRenameRequest([name: 'shoplist newname'])
        Long shoplistId = 1L

        when:
        shoplistController.renameShoplist(shoplistId, request)

        then:
        1 * shoplistService.renameShoplist(shoplistId, request.getName())
    }

    def 'should throw ObjectNotFoundException when rename not existed shoplist'() {
        given:
        ShoplistRenameRequest request = new ShoplistRenameRequest([name: 'shoplist newname'])
        Long shoplistId = 1L

        when:
        shoplistController.renameShoplist(shoplistId, request)

        then:
        1 * shoplistService.renameShoplist(shoplistId, request.getName()) >> {
            throw new ObjectNotFoundException('Shoplist not found')
        }

        and:
        thrown ObjectNotFoundException
    }

    def 'should delete shoplist'() {
        given:
        Long shoplistId = 1L

        when:
        shoplistController.deleteShoplist(shoplistId)

        then:
        1 * shoplistService.deleteShoplist(shoplistId)
    }

    def 'should throw ObjectNotFoundException when delete not existed shoplist'() {
        given:
        Long shoplistId = 1L

        when:
        shoplistController.deleteShoplist(shoplistId)

        then:
        1 * shoplistService.deleteShoplist(shoplistId) >> {
            throw new ObjectNotFoundException('Shoplist not found')
        }

        and:
        thrown ObjectNotFoundException
    }
}
