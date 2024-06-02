package com.springExamenopdracht;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import domain.Author;
import domain.Authority;
import domain.Location;
import domain.User;
import domain.Book;
import repository.AuthorRepository;
import repository.AuthorityRepository;
import repository.BookRepository;
import repository.LocationRepository;
import repository.UserRepository;

@Component
public class InitDataConfig implements CommandLineRunner{
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Authority adminRole = new Authority("ROLE_ADMIN");
		Authority userRole = new Authority("ROLE_USER");
		
		User admin = new User("admin", "adminFirstname", "adminLastname", "admin@gmail.com", "$2a$10$vtwFM9/.Q6rWBRReI/qg0uZAhBvVOc4XUZsP846wT1UzsaSMIEtuK", 0);
		User user1 = new User("userName1", "Sebastiaan", "Delodder", "sebastiaan.delodder@student.hogent.be", "$2a$10$Kbu5oI0t40T0MB46ZGXqLO2ST2ZD8GfJgs8PL9FVtwsZUJfFdJrre", 5);
		User user2 = new User("userName2", "Thomas", "Coppens", "thomas.coppens@gmail.com", "$2a$10$Kbu5oI0t40T0MB46ZGXqLO2ST2ZD8GfJgs8PL9FVtwsZUJfFdJrre", 7);
		
		admin.addRole(adminRole);
		user1.addRole(userRole);
		user2.addRole(userRole);
		
		Author author1 = new Author("J.K.", "Rowling");
		Author author2 = new Author("Stephen", "King");
		Author author3 = new Author("J.R.R.", "Tolkien");
		Author author4 = new Author("Charles", "Dickens");
		Author author5 = new Author("Terry", "Pratchett");
		Author author6 = new Author("Neil", "Gaiman");
		
		
		Location location1 = new Location("50", "100", "Ghent");
		Location location2 = new Location("61", "116", "Antwerp");
		Location location3 = new Location("76", "130", "Brussels");
		Location location4 = new Location("84", "175", "Bruges");
		Location location5 = new Location("105", "176", "Durbuy");
		Location location6 = new Location("123", "180", "Leuven");
		Location location7 = new Location("150", "201", "Liège");
		Location location8 = new Location("161", "241", "Mechelen");
		Location location9 = new Location("200", "259", "Ypres");
		Location location10 = new Location("248", "298", "Namur");
		Location location11 = new Location("250", "300", "Kortrijk");
		
		Book harry1 = new Book("9781856134033", "Harry Potter and the Philosopher's Stone", new BigDecimal(9.99), "https://media.harrypotterfanzone.com/philsophers-stone-uk-childrens-edition.jpg");
		Book harry2 = new Book("9780747538493", "Harry Potter and the Chamber of Secrets", new BigDecimal(9.99), "https://i.pinimg.com/originals/e7/6e/d0/e76ed082d852c4fa97cbcf6018cd384c.jpg");
		Book shining = new Book("9780345806789", "The Shining", new BigDecimal(14.99), "https://th.bing.com/th/id/OIP.4J21OZt86GHq_HW7D0aWHwHaMK?pid=ImgDet&rs=1");
		Book misery = new Book("9781501143106", "Misery", new BigDecimal(14.99), "https://th.bing.com/th/id/R.8cf325401479e367ceba8d3f32c05a1c?rik=ybk7L8RNQKZEow&pid=ImgRaw&r=0");
		Book hobbit = new Book("9780547928227", "The Hobbit", new BigDecimal(9.99), "https://i0.wp.com/brokebybooks.com/wp-content/uploads/2017/09/The_Hobbit.jpg?ssl=1");
		Book lotr = new Book("9780544273443", "The Lord of the Rings", new BigDecimal(29.99), "https://th.bing.com/th/id/OIP.6BxmT9txKUtF4Ewta-DOOQHaLH?pid=ImgDet&rs=1");
		Book christmas = new Book("9781503212831", "A Christmas Carol", new BigDecimal(4.99), "https://th.bing.com/th/id/R.4f7c4afc6bccf1d4496f41d6833e3820?rik=ME8icPTJvCj90g&pid=ImgRaw&r=0");
		Book bleak = new Book("9780141439723", "Bleak House", new BigDecimal(7.99), "https://th.bing.com/th/id/R.4647f69d58e649d66ca079a4c11ccf3c?rik=0JnI4j1Xh6cG7w&pid=ImgRaw&r=0");
		Book goodOmens = new Book("9780060853983", "Good Omens", new BigDecimal(12.99), "https://th.bing.com/th/id/R.e320931396eb3bf9416bd594e8ca59a5?rik=r8UTXQdFFmu%2fxw&riu=http%3a%2f%2fprodimage.images-bn.com%2fpimages%2f9780060853983_p0_v6_s1200x630.jpg&ehk=BzB%2fYvMGOr4JvRWFmdm9xA8oPOyGfrL505wOgJWX0TQ%3d&risl=&pid=ImgRaw&r=0");
		
		
		harry1.addAuthor(author1);
		harry2.addAuthor(author1);
		shining.addAuthor(author2);
		misery.addAuthor(author2);
		hobbit.addAuthor(author3);
		lotr.addAuthor(author3);
		christmas.addAuthor(author4);
		bleak.addAuthor(author4);
		goodOmens.addAuthor(author5);
		goodOmens.addAuthor(author6);
		
		harry1.addLocation(location1);
		harry2.addLocation(location2);
		harry2.addLocation(location3);
		shining.addLocation(location4);
		misery.addLocation(location5);
		hobbit.addLocation(location6);
		lotr.addLocation(location7);
		christmas.addLocation(location8);
		bleak.addLocation(location9);
		goodOmens.addLocation(location10);
		goodOmens.addLocation(location11);
		
		authorityRepository.save(adminRole);
		authorityRepository.save(userRole);
		
		userRepository.save(admin);
		userRepository.save(user1);
		userRepository.save(user2);
		
		authorRepository.save(author1);
		authorRepository.save(author2);
		authorRepository.save(author3);
		authorRepository.save(author4);
		authorRepository.save(author5);
		authorRepository.save(author6);
		
		
		locationRepository.save(location1);
		locationRepository.save(location2);
		locationRepository.save(location3);
		locationRepository.save(location4);
		locationRepository.save(location5);
		locationRepository.save(location6);
		locationRepository.save(location7);
		locationRepository.save(location8);
		locationRepository.save(location9);
		locationRepository.save(location10);
		locationRepository.save(location11);
		
		bookRepository.save(harry1);
		bookRepository.save(harry2);
		bookRepository.save(shining);
		bookRepository.save(misery);
		bookRepository.save(hobbit);
		bookRepository.save(lotr);
		bookRepository.save(christmas);
		bookRepository.save(bleak);
		bookRepository.save(goodOmens);
		
	}

}
