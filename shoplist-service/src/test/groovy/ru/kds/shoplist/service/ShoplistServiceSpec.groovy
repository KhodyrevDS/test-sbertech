package ru.kds.shoplist.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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

    def 'should find shoplists'() {
        given:
        Long shoplist1Id = 1L
        Long shoplist2Id = 2L
        Long shoplist3Id = 3L
        Pageable pageable = new PageRequest(0, 10)

        when:
        Page<Shoplist> shoplists = shoplistService.findShoplists(pageable)

        then:
        1 * shoplistRepository.findAll(pageable) >> new PageImpl<>([
                new Shoplist([id: shoplist1Id, name: 'shoplist1']),
                new Shoplist([id: shoplist2Id, name: 'shoplist2']),
                new Shoplist([id: shoplist3Id, name: 'shoplist3'])
        ])

        and:
        shoplists.content.size() == 3
        shoplists.content.get(0).id == 1L
        shoplists.content.get(1).id == 2L
        shoplists.content.get(2).id == 3L
    }
}
