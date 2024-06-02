package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NamedQueries({
	@NamedQuery(name = "User.findByUsername",
			query = "SELECT u FROM User u WHERE u.username = :name")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "authority")
	private Authority authority;
	
	@OneToMany
	private List<Book> favorites = new ArrayList<>();
	
	private int maxFavorites = -1;
	
	private String enabled = "1";
	
	public User(String username, String firstname, String lastname, String email, String password, int maxFavorites) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.maxFavorites = maxFavorites;
	}
	
	public void addRole(Authority role) {
		this.authority = role;
	}
	
	public int getTotalFavorites() {
		return this.favorites.size();
	}
	
	public void addFavorite(Book book) {
		if (favorites.size() >= maxFavorites) {
	        throw new IllegalStateException("You've already reached the limit of your total favorites");
	    }
        favorites.add(book);
    }
	
	public void removeFavorite(Book book) {
        favorites.remove(book);
    }
    
    public boolean isFavorite(Book book) {
        return favorites.contains(book);
    }
}
