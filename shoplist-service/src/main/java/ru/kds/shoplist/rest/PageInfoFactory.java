package ru.kds.shoplist.rest;

import org.springframework.data.domain.Page;
import ru.kds.shoplist.contract.PageInfo;

/**
 * Page info factory
 */
public final class PageInfoFactory {

    /**
     * Class constructor
     */
    private PageInfoFactory() {
    }

    /**
     * Create page info
     *
     * @param page the page
     * @return the page info
     */
    public static PageInfo createPageInfo(Page page) {
        return new PageInfo(page.getContent().size(), page.getTotalElements(), page.getTotalPages(), page.getNumber());
    }
}
