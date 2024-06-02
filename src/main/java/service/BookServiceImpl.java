package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Author;
import domain.Book;
import repository.AuthorRepository;
import repository.BookRepository;

public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookData;
	
	@Autowired
	private AuthorRepository authorData;

	@Override
	public List<Book> getBooksFromAuthor(String authID) {
		Optional<Author> author = authorData.findById(authID);
		Author auth = author.get();

		return auth.getBooks();
	}

	@Override
	public Book getBookWithISBN(String isbn) {
		Optional<Book> book = bookData.findById(isbn);
		return book.get();
	}


}
