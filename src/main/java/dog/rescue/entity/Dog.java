package dog.rescue.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Dog { // child of location in DB
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
// id, name, age and color instance vars
	private Long dogId;
	
	@EqualsAndHashCode.Exclude // when lombok creates .equals method and hashcode, leave name, age and color out
	private String name;
	
	@EqualsAndHashCode.Exclude
	private int age;
	
	@EqualsAndHashCode.Exclude
	private String color;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude // exclude incase wanting to print
	// owned side, specify Many-1
	@ManyToOne
	// we're creating tables, not JPA so spec join column
	@JoinColumn(name = "location_id", nullable = false) // column in dog tbl, if have dog, must have location
	// point back to Location, JPA requirement 
	private Location location;

	// owning side, many to many
	@EqualsAndHashCode.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST) // persist type - do not delete rows out of breed tbl if del dog, but do del out of join tbl rows
	@JoinTable(
			name = "dog_breed",
			joinColumns = @JoinColumn(name = "dog_id"),
			inverseJoinColumns = @JoinColumn(name = "breed_id")
			)
	private Set<Breed> breeds = new HashSet<>();
	
	
	
}
