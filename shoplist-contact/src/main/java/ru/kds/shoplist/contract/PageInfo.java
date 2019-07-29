package ru.kds.shoplist.contract;

import java.io.Serializable;

/**
 * Page info
 */
public class PageInfo implements Serializable {

    private static final long serialVersionUID = -6475716268507426461L;

    private int size;

    private long totalElements;

    private int totalPages;

    private int number;

    /**
     * Class constructor
     */
    public PageInfo() {
    }

    /**
     * Class constructor
     *
     * @param size the number of elements in content
     * @param totalElements the total amount of elements
     * @param totalPages the number of total pages
     * @param number the number of the current slice
     */
    public PageInfo(int size, long totalElements, int totalPages, int number) {
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
