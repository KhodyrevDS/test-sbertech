package ru.kds.shoplist.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShoplistRepository extends PagingAndSortingRepository<Shoplist, Long> {

}
