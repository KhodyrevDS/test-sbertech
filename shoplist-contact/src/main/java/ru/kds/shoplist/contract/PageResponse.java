package ru.kds.shoplist.contract;

import java.io.Serializable;
import java.util.List;

public class PageResponse<T extends Serializable> extends ListResponse<T> {

    private static final long serialVersionUID = 8356403369469189017L;

    private PageInfo pageInfo;

    /**
     * Class constructor
     */
    public PageResponse() {
    }

    /**
     * Class constructor
     *
     * @param content the content
     * @param pageInfo the page info
     */
    public PageResponse(List<T> content, PageInfo pageInfo) {
        super(content);
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
