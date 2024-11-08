package com.todolistapi.todolistapi.entity;

import java.util.List;

public class ResponsePage {
    private List<Todo> data;
    private int page;
    private int limit;
    private int total;

    public ResponsePage() {
    }

    public ResponsePage(List<Todo> data, int page, int limit, int total) {
        this.data = data;
        this.page = page;
        this.limit = limit;
        this.total = total;
    }

    public List<Todo> getData() {
        return data;
    }

    public void setData(List<Todo> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
