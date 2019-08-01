package ru.kds.shoplist.rest

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import ru.kds.shoplist.contract.ShoplistCreateRequest
import ru.kds.shoplist.contract.ShoplistCreateResponse
import ru.kds.shoplist.contract.ShoplistRenameRequest
import ru.kds.shoplist.domain.Shoplist
import ru.kds.shoplist.domain.ShoplistRepository
import spock.lang.Shared
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Integration specification for {@link ShoplistController}
 */
@Unroll
class ShoplistControllerIt extends AbstractIt {

    @Autowired
    private ShoplistController shoplistController

    @Autowired
    private ShoplistRepository shoplistRepository

    @Autowired
    MockMvc mvc

    @Shared
    private ObjectMapper objectMapper = new ObjectMapper()

    @Shared
    private Long shoplistId

    def 'should create shoplist'() {
        given:
        ShoplistCreateRequest request = new ShoplistCreateRequest([name: 'shoplist name'])

        when:
        MvcResult mvcResult = mvc.perform(post("/api/v1/shoplist")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk()).andReturn()

        then:
        mvcResult.getResponse().getContentAsString() != null

        when:
        shoplistId = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShoplistCreateResponse).id
        Shoplist shoplist = shoplistRepository.findById(shoplistId).get()

        then:
        shoplist.name == request.name
    }

    def 'should rename shoplist'() {
        given:
        ShoplistRenameRequest request = new ShoplistRenameRequest([name: 'shoplist newname'])

        when:
        mvc.perform(post("/api/v1/shoplist/{id}", shoplistId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
        Shoplist shoplist = shoplistRepository.findById(shoplistId).get()

        then:
        shoplist.name == request.name
    }

    def 'should delete shoplist'() {
        when:
        mvc.perform(delete("/api/v1/shoplist/{id}", shoplistId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
        Optional<Shoplist> shoplist = shoplistRepository.findById(shoplistId)

        then:
        !shoplist.isPresent()
    }
}
