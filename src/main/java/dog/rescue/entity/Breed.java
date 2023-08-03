package dog.rescue.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Breed {
// instance vars
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long breedId;
	private String name;
	
	// recursive attribute that pts back to dog
	// vvv exclude field from tostring and hashcode methods
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "breeds") // specs the specific java field breeds
	private Set<Dog> dogs = new HashSet<>();
}
