package dog.rescue.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data // lombok annotation gens getters/setters, toString, etc
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationId;

	private String businessName;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String phone;

// one to many relatnship with dogs held by a Set
//...  define owning side of 1-many relnship as well as cascade type in ()
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true) // if saving a location, saves dogs, same happens for
																// deleting
	private Set<Dog> dogs = new HashSet<>();
}
