package ru.kds.shoplist.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

    Optional<Item> findByIdAndShoplist_id(Long id, Long shoplistId);

    Page<Item> findByShoplist_id(Long id, Pageable pageable);
}
