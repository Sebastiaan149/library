package com.springExamenopdracht;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Author;
import domain.Book;
import jakarta.validation.Valid;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.LocationRepository;
import validator.BookValidation;

@Controller
@RequestMapping("/editBook/add")
public class BookEditorController {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private BookValidation isbnValidation;
	
	@GetMapping
	public String showSaveBook(Model model, Authentication authentication) {
		List<Author> allAuthors = (List<Author>) authorRepository.findAll();
		List<String> listRoles = authentication.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority).toList();
		
		model.addAttribute("allAuthors", allAuthors);
		model.addAttribute("username", authentication.getName());
		model.addAttribute("role", listRoles.get(0).substring(5));
		model.addAttribute("newBook", new Book());
		model.addAttribute("locations", locationRepository.findAll());
		return "editor/addBook";
	}
	
	@PostMapping
    public String processNumbers(@Valid Book newBook, BindingResult result, Model model, Authentication authentication) {
		
		isbnValidation.validate(newBook, result);
		
        if (result.hasErrors()) {
        	List<Author> allAuthors = (List<Author>) authorRepository.findAll();
    		List<String> listRoles = authentication.getAuthorities()
    				.stream().map(GrantedAuthority::getAuthority).toList();
        	
        	model.addAttribute("allAuthors", allAuthors);
        	model.addAttribute("role", listRoles.get(0).substring(5));
    		model.addAttribute("newBook", newBook);
    		
    		//LAAT DIT STAAN OM VALIDATION TE ZIEN
    		System.out.println(result);
    		
        	return "editor/addBook";
        }
        
        if (newBook.getPurchasePrice() == null) {
        	newBook.setPurchasePrice(new BigDecimal(0.00));
        }
        bookRepository.save(newBook);
        return "redirect:/catalogue";
    }
}
