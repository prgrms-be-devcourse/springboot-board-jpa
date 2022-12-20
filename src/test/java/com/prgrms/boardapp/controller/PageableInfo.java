package com.prgrms.boardapp.controller;

public enum PageableInfo {
    PAGEABLE("pageable", "Pageable"),
    LAST("last", "Last"),
    TOTAL_PAGES("totalPages", "TotalPages"),
    TOTAL_ELEMENTS("totalElements", "TotalElements"),
    SIZE("size", "Size"),
    SORT("sort", "정렬 매개변수"),
    SORT_EMPTY("sort.empty", "empty"),
    SORT_SORTED("sort.sorted", "sorted"),
    SORT_UNSORTED("sort.unsorted", "unsorted"),
    NUMBER("number", "Number"),
    FIRST("first", "First"),
    NUM_OF_ELEMENTS("numberOfElements", "NUM_OF_ELEMENTS"),
    EMPTY("empty", "EMPTY");

    private final String field;
    private final String description;

    PageableInfo(String field, String description) {
        this.field = field;
        this.description = description;
    }

    public String getField() {
        return field;
    }

    public String getDescription() {
        return description;
    }
}
