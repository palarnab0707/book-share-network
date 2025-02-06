package com.social.book_network.book;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Integer id;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String owner;
    private String title;
    private byte[] cover;
    private double rate;
    private boolean archived;
    private boolean shareable;

}
