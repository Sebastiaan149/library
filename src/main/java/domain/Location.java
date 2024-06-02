package domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NamedQuery(name = "Location.locationAlreadyExists",
	query = "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Location l WHERE l.localCode1 = :code1 AND " +
			"l.localCode2 = :code2 AND l.name = :name")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long locationId;
	private String localCode1;
	private String localCode2;
	private String name;
	
	public Location(String localCode1, String localCode2, String name) {
		this.localCode1 = localCode1;
		this.localCode2 = localCode2;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Location " + name + 
				":     " + localCode1 +
				" - " + localCode2;
	}
}
