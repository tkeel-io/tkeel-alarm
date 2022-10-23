package com.alarm.tkeel.utils;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/18/14:45
 */
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int page_num;
    private int page_size;
    //    private int size;
//    private int startRow;
//    private int endRow;
    private long total;
    private int pages;
    private List<T> list;
//    private int prePage;
//    private int nextPage;
//    private boolean isFirstPage;
//    private boolean isLastPage;
//    private boolean hasPreviousPage;
//    private boolean hasNextPage;
//    private int navigatePages;
//    private int[] navigatepageNums;
//    private int navigateFirstPage;
//    private int navigateLastPage;

    public PageInfo() {
//        this.isFirstPage = false;
//        this.isLastPage = false;
//        this.hasPreviousPage = false;
//        this.hasNextPage = false;
    }

    public PageInfo(List<T> list) {
        this(list, 8);
    }

    public PageInfo(List<T> list, int navigatePages) {
//        this.isFirstPage = false;
//        this.isLastPage = false;
//        this.hasPreviousPage = false;
//        this.hasNextPage = false;
        if (list instanceof Page) {
            Page page = (Page)list;
            this.page_num = page.getPageNum();
            this.page_size = page.getPageSize();
            this.pages = page.getPages();
            this.list = page;
//            this.size = page.size();
            this.total = page.getTotal();
//            if (this.size == 0) {
//                this.startRow = 0;
//                this.endRow = 0;
//            } else {
//                this.startRow = page.getStartRow() + 1;
//                this.endRow = this.startRow - 1 + this.size;
//            }
        } else if (list instanceof Collection) {
            this.page_num = 1;
            this.page_size = list.size();
            this.pages = this.page_size > 0 ? 1 : 0;
            this.list = list;
//            this.size = list.size();
            this.total = (long)list.size();
//            this.startRow = 0;
//            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
        }

    }


    public int getPage_num() {
        return this.page_num;
    }

    public void setPage_num(int pageNum) {
        this.page_num = pageNum;
    }

    public int getPage_size() {
        return this.page_size;
    }

    public void setPage_size(int pageSize) {
        this.page_size = pageSize;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer("PageInfo{");
        sb.append("page_num=").append(this.page_num);
        sb.append(", page_size=").append(this.page_size);
//        sb.append(", size=").append(this.size);
//        sb.append(", startRow=").append(this.startRow);
//        sb.append(", endRow=").append(this.endRow);
        sb.append(", total=").append(this.total);
        sb.append(", pages=").append(this.pages);
        sb.append(", list=").append(this.list);
//        sb.append(", navigatepageNums=");
//        if (this.navigatepageNums == null) {
//            sb.append("null");
//        } else {
//            sb.append('[');
//
//            for(int i = 0; i < this.navigatepageNums.length; ++i) {
//                sb.append(i == 0 ? "" : ", ").append(this.navigatepageNums[i]);
//            }
//
//            sb.append(']');
//        }

        sb.append('}');
        return sb.toString();
    }
}
