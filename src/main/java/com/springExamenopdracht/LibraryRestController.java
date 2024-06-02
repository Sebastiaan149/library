package com.springExamenopdracht;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Book;
import service.BookService;

@RestController
@RequestMapping(value = "/rest")
public class LibraryRestController {
	@Autowired
	private BookService bookService;
	
	@GetMapping(value = "/book/author/{id}")
	public List<Book> getBooksFromAuthor(@PathVariable("id") String authID){
		return bookService.getBooksFromAuthor(authID);
	}
	
	@GetMapping(value = "/book/isbn/{isbn}")
	public Book getBookWithISBN(@PathVariable("isbn") String isbn){
		return bookService.getBookWithISBN(isbn);
	}
}
