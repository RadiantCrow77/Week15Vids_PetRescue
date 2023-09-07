package dog.rescue.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import dog.rescue.DogRescueApplication;
import dog.rescue.controller.model.LocationData;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = DogRescueApplication.class)
@ActiveProfiles("test") // sets profile to test so we can create application-test.yaml
@Sql(scripts = { "classpath:schema.sql", "classpath:data.sql" }) // Spring Boot to load data.sql and schema.sql before
																	// each test
@SqlConfig(encoding = "utf-8") // some non ASCII chars in data.sql -> tell Spring to load data.sql using UTF8
class RescueControllerTest extends RescueServiceTestSupport {

	@Test
	void testInsertLocation() {
		// Given: A location request
		LocationData request = buildInsertLocation(1);
		LocationData expected = buildInsertLocation(1);

		// can examine request and expected objects that got built through test:
//		System.out.println("request "+request);
//		System.out.println("expected "+expected);
		// or set a break point at LocationData actual line, then Debug as > JUnit Test
		// ctrl + shift + i over expected ^^

		// When: The location is added to the location table
		LocationData actual = insertLocation(request);

		// Then: The location returned is what we expected
		assertThat(actual).isEqualTo(expected);

		// And: There is one row in the location table
		assertThat(rowsInLocationTable()).isOne();
	}

	@Test
	void testRetrieveLocation() {
		// Given: A location
		// insert location before we can retrieve it
		LocationData location = insertLocation(buildInsertLocation(1));
		// expected obj
		LocationData expected = buildInsertLocation(1);

		// When: the location is retrieved by location ID
		LocationData actual = retrieveLocation(location.getLocationId());

		// Then: the actual location = expected location
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testRetrieveAllLocations() {
		// Given: 2 locations
		List<LocationData> expected = insertTwoLocations(); // originally: expected : location 1, because of our sort
															// ...

		// When: all locations are retrieved
		List<LocationData> actual = retrieveAllLocations(); // ... originally: actual : location 2 ... fix this by
															// sorting in our then clause

		// Then: the retrieved/actual locations = the expected locations
		assertThat(sorted(actual)).isEqualTo(sorted(expected));
		// NOTE: can do this method with objects OR lists, like here ^^^^

	}

	@Test
	void testUpdateLocation() {
		// Given: a location and an update req
		insertLocation(buildInsertLocation(1));
		LocationData expected = buildUpdateLocation();

		// When: location is updated
		LocationData actual = updateLocation(expected);

		// Then: location is returned as expected
		assertThat(actual).isEqualTo(expected);

		// And: there is 1 row in the location table
		assertThat(rowsInLocationTable()).isOne();
	}

	@Test
	void testDeleteLocationWithDogs() {
		// Given: a location and 2 dogs
		LocationData location = insertLocation(buildInsertLocation(1));
		Long locationId = location.getLocationId();
		
		insertDog(1);
		insertDog(2);
		
		
		assertThat(rowsInLocationTable()).isOne();
		assertThat(rowsInDogTable()).isEqualTo(2);
		assertThat(rowsInDogBreedTable()).isEqualTo(4);
		int breedRows = rowsInBreedTable();
		
		// When: location is deleted
		deleteLocation(locationId);

		// Then: there are no location, dog, or dog_breed rows
		assertThat(rowsInLocationTable()).isZero();
		assertThat(rowsInDogTable()).isZero();
		assertThat(rowsInDogBreedTable()).isZero();
		
		
		// And: the # of breed rows has not changed
		assertThat(rowsInBreedTable()).isEqualTo(breedRows);
		
		
		// Mike's Version
		// Given: two dogs and a location
//	    Long locationId = insertLocationAndTwoDogs();
//	    int breedRows = rowsInBreedTable();
//	    assertLocationAndDogRowsAreAddedCorrectly();
//	    // When: the location is deleted
//	    deleteLocation(locationId);
//	    // Then: there are no location, dog, or dog_breed rows
//	    assertLocationAndDogRowsAreGoneAfterDeletion();
//	    // And: the number of breed rows has not changed
//	    assertThat(rowsInBreedTable()).isEqualTo(breedRows);
		
	}

	


}
