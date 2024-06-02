package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private long authorId;
	
	@Getter private String firstname;
	
	@Getter private String lastname;
	
	@ManyToMany(mappedBy = "authors")
	@JsonBackReference
	private List<Book> books = new ArrayList<>();
	
	public Author(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return authorId == other.authorId;
	}
}
