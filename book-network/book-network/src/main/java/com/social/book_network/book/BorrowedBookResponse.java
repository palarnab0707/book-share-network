package com.social.book_network.book;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookResponse {
    private Integer id;
    private String authorName;
    private String isbn;
    private String title;
    private double rate;
    private boolean returned;
    private boolean returnApproved;
}
