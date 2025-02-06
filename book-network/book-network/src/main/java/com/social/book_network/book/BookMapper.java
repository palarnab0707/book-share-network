package com.social.book_network.book;

import com.social.book_network.file.FileUtils;
import com.social.book_network.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequest request) {

        return Book.builder()
                .id(request.id())
                .authorName(request.authorName())
                .title(request.title())
                .synopsis(request.synopsis())
                .shareable(request.shareable())
                .isbn(request.isbn())
                .archived(false)
                .build();
    }

    public BookResponse toBookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .archived(book.isArchived())
                .isbn(book.getIsbn())
                .rate(book.getRate())
                .shareable(book.isShareable())
                .title(book.getTitle())
                .synopsis(book.getSynopsis())
                .authorName(book.getAuthorName())
                .owner(book.getOwner().getFullName())
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history){
        return BorrowedBookResponse.builder()
                .id(history.getBook().getId())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().getRate())
                .title(history.getBook().getTitle())
                .authorName(history.getBook().getAuthorName())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }
}
