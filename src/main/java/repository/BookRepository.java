package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Book;

public interface BookRepository extends CrudRepository<Book, String>{

	boolean isAlreadyUsedISBN(
			@Param("isbn") String isbn);
	
	Book findByISBN(@Param("isbn") String isbn);
}
