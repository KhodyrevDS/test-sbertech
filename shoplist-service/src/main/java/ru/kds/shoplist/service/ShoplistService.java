package ru.kds.shoplist.service;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kds.shoplist.domain.Shoplist;
import ru.kds.shoplist.domain.ShoplistRepository;

/**
 * Shoplist service
 */
@Service
public class ShoplistService {

    private final ShoplistRepository shoplistRepository;

    public ShoplistService(ShoplistRepository shoplistRepository) {
        Validate.notNull(shoplistRepository, "parameter shoplistRepository is null");

        this.shoplistRepository = shoplistRepository;
    }

    /**
     * Create shoplist
     *
     * @param name the shoplist name
     * @return the shoplist
     */
    @Transactional
    public Shoplist createShoplist(String name) {
        Validate.notBlank(name, "parameter name is null or blank");

        Shoplist shoplist = new Shoplist();
        shoplist.setName(name);

        return shoplistRepository.save(shoplist);
    }

    /**
     * Rename shoplist
     *
     * @param id the shoplist identifier
     * @param name the shoplist name
     * @throws ObjectNotFoundException when shoplist not exist
     */
    @Transactional
    public void renameShoplist(Long id, String name) throws ObjectNotFoundException {
        Validate.notNull(id, "parameter id is null");
        Validate.notBlank(name, "parameter name is null or blank");

        Shoplist shoplist = shoplistRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Shoplist not exist"));
        shoplist.setName(name);

        shoplistRepository.save(shoplist);
    }

    /**
     * Delete shoplist
     *
     * @param id the shoplist identifier
     * @throws ObjectNotFoundException when shoplist not exist
     */
    @Transactional
    public void deleteShoplist(Long id) throws ObjectNotFoundException {
        Validate.notNull(id, "parameter id is null");
        Shoplist shoplist = shoplistRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Shoplist not exist"));

        shoplistRepository.delete(shoplist);
    }

    /**
     * Find shoplists
     *
     * @param pageable the page info
     * @return the list of shoplist
     */
    public Page<Shoplist> findShoplists(Pageable pageable) {
        return shoplistRepository.findAll(pageable);
    }
}
