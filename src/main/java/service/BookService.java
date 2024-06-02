package service;

import java.util.List;

import domain.Book;


public interface BookService {

	List<Book> getBooksFromAuthor(String authID);

	Book getBookWithISBN(String isbn);

}
