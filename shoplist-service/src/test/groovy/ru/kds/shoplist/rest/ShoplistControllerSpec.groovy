package ru.kds.shoplist.rest


import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import ru.kds.shoplist.contract.PageResponse
import ru.kds.shoplist.contract.ShoplistCreateRequest
import ru.kds.shoplist.contract.ShoplistCreateResponse
import ru.kds.shoplist.contract.ShoplistRenameRequest
import ru.kds.shoplist.contract.ShoplistRest
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

    def 'should find shoplists'() {
        given:
        Long shoplist1Id = 1L
        Long shoplist2Id = 2L
        Long shoplist3Id = 3L
        Pageable pageable = new PageRequest(0, 10)

        when:
        PageResponse<ShoplistRest> shoplistRests = shoplistController.findShoplists(pageable)

        then:
        1 * shoplistService.findShoplists(pageable) >> new PageImpl<>([
                new Shoplist([id: shoplist1Id, name: 'shoplist1']),
                new Shoplist([id: shoplist2Id, name: 'shoplist2']),
                new Shoplist([id: shoplist3Id, name: 'shoplist3'])
        ])

        and:
        shoplistRests.content.size() == 3
        shoplistRests.content.get(0).id == 1L
        shoplistRests.content.get(1).id == 2L
        shoplistRests.content.get(2).id == 3L
    }
}
