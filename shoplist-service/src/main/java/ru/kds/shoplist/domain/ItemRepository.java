package ru.kds.shoplist.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

    Page<Item> findByShoplist_id(Long id, Pageable pageable);

    @Query("SELECT count(i) FROM Item i WHERE i.checked=false AND i.template = ?1")
    long countActiveSheduledItem(ItemTemplate itemTemplate);

    Page<Item> findByCheckedFalse(Pageable pageable);
}
