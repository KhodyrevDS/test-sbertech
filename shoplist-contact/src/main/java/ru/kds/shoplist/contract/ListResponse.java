package ru.kds.shoplist.contract;

import java.io.Serializable;
import java.util.List;

/**
 * List response
 */
public class ListResponse<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 2697181659667542797L;

    private List<T> content;

    /**
     * Class constructor
     */
    public ListResponse() {
    }

    /**
     * Class constructor
     *
     * @param content the content
     */
    public ListResponse(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
