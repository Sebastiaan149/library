package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NamedQueries({
	@NamedQuery(name = "Book.isAlreadyUsedISBN",
			query = "SELECT CASE WHEN EXISTS (SELECT 1 FROM Book b WHERE b.isbn = :isbn) THEN true ELSE false END FROM Book"),
	@NamedQuery(name = "Book.findByISBN",
			query = "SELECT b FROM Book b WHERE b.isbn = :isbn")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Book implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String isbn;
	
	@NotBlank
	private String title;
	
	private String coverLink;
	
	@ManyToMany
	@JsonManagedReference
	private List<Author> authors = new ArrayList<>();
	
	@NumberFormat(pattern = "0.00")
	private BigDecimal purchasePrice;
	
	private int totalStars = 0;
	
	@OneToMany
	private List<Location> locations = new ArrayList<>();
	
	public Book(String isbn, String title, BigDecimal purchasePrice, String coverLink) {
		this.isbn = isbn;
		this.title = title;
		this.purchasePrice = purchasePrice;
		this.coverLink = coverLink;
	}
	
	public void addAuthor(Author author) {
		this.authors.add(author);
	}
	
	public void addLocation(Location location) {
		this.locations.add(location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(isbn, other.isbn);
	}
	
	public void addStar() {
		this.totalStars++;
	}
	
	public void removeStar() {
		this.totalStars--;
	}
	
}
