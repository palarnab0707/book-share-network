package com.social.book_network.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private int number; // page number
    private int size; // total element in one page
    private long totalElements;
    private int totalPages;
    private boolean first; // is first page
    private boolean last; // is last page

}
