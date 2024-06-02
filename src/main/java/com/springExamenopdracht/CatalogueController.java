package com.springExamenopdracht;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Book;
import domain.User;
import repository.BookRepository;
import repository.UserRepository;

@Controller
@RequestMapping("/catalogue")
public class CatalogueController {
	
	@Autowired 
	private BookRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String showCatalogue(Model model, Authentication authentication) {
		model.addAttribute("bookList", repository.findAll());
		
		List<String> listRoles = authentication.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority).toList();


		model.addAttribute("username", authentication.getName());
		model.addAttribute("role", listRoles.get(0).substring(5));
		return "/catalogue/catalogue";
	}
	
	@GetMapping(value = "/{isbn}")
	public String showBook(@PathVariable("isbn") String isbn, Model model, Authentication authentication) {
		Book book = repository.findByISBN(isbn);
		
		if (book == null) {
			return "redirect:/catalogue";
		}
		
		model.addAttribute("book", book);
		
		List<String> listRoles = authentication.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority).toList();


		model.addAttribute("username", authentication.getName());
		model.addAttribute("role", listRoles.get(0).substring(5));
		model.addAttribute("user", userRepository.findByUsername(authentication.getName()));
		return "catalogue/bookDetails";
	}
	
	@PostMapping(value = "/{isbn}/{value}")
	public String addFavorite(@PathVariable("isbn") String isbn,
			@PathVariable("value") boolean value, Model model, Authentication authentication) {
		Book book = repository.findByISBN(isbn);
		
		if (book == null) {
			return "redirect:/catalogue";
		}
		
		User currentUser = userRepository.findByUsername(authentication.getName());
		if (value) {
			currentUser.addFavorite(book);
			book.addStar();
		} else {
			currentUser.removeFavorite(book);
			book.removeStar();
		}
		userRepository.save(currentUser);
		repository.save(book);
		
		model.addAttribute("book", book);
		
		List<String> listRoles = authentication.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority).toList();


		model.addAttribute("username", currentUser.getUsername());
		model.addAttribute("role", listRoles.get(0).substring(5));
		model.addAttribute("user", userRepository.findByUsername(authentication.getName()));
		return "catalogue/bookDetails";
	}
	
	@GetMapping(value = "/popular")
	public String showBook(Model model, Authentication authentication) {
		List<Book> rankedBooks = StreamSupport.stream(repository.findAll().spliterator(), false)
			    .sorted(Comparator.comparing(Book::getTotalStars).reversed()
			    		.thenComparing(Book::getTitle))
			    .collect(Collectors.toList());

		model.addAttribute("rankedBooks", rankedBooks);
		
		List<String> listRoles = authentication.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority).toList();


		model.addAttribute("username", authentication.getName());
		model.addAttribute("role", listRoles.get(0).substring(5));
		return "catalogue/popular";
	}
}
