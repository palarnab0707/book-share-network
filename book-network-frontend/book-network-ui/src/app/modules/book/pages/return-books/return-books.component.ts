import {Component, OnInit} from '@angular/core';
import {BorrowedBookResponse} from "../../../../services/models/borrowed-book-response";
import {PageResponseBorrowedBookResponse} from "../../../../services/models/page-response-borrowed-book-response";
import {FeedbackRequest} from "../../../../services/models/feedback-request";
import {BookService} from "../../../../services/services/book.service";
import {FeedbackService} from "../../../../services/services/feedback.service";

@Component({
  selector: 'app-return-books',
  templateUrl: './return-books.component.html',
  styleUrls: ['./return-books.component.scss']
})
export class ReturnBooksComponent implements OnInit {
  page: number = 0;
  size: number = 5;
  returnedBook: PageResponseBorrowedBookResponse = {};
  message= '';
  level= 'success';


  constructor(private bookService: BookService, private feedbackService: FeedbackService) {
  }

  ngOnInit(): void {
    this.findAllReturnedBooks();
  }


  private findAllReturnedBooks() {
    this.bookService.findAllReturnedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (result) => {
        this.returnedBook = result;
      }
    })
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllReturnedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllReturnedBooks();
  }

  goToPage(index: number) {
    this.page = index;
    this.findAllReturnedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllReturnedBooks();
  }

  goToLastPage() {
    this.page = this.returnedBook.totalPages as number - 1;
    this.findAllReturnedBooks();
  }

  get isLastPage(): boolean {
    return this.page == this.returnedBook.totalPages as number - 1;
  }

  approveReturnBook(book: BorrowedBookResponse) {
    if(!book.returned){
      this.message = 'The book yet to return';
      this.level = 'error';
      return;
    }
    this.bookService.approveReturnBorrowedBook({
      "book-id": book.id as number,
    }).subscribe({
      next: (result) => {
        this.message = 'Returned Book was approved';
        this.level = 'success';
      },
      error: (err) => {
        this.message = err.message;
        this.level = 'error';
      }
    })
  }
}
