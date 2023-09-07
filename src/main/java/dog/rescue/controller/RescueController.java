package dog.rescue.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dog.rescue.controller.model.LocationData;
import dog.rescue.service.RescueService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dog_rescue")
@Slf4j
public class RescueController {
	
	@Autowired
	private RescueService rescueService;
	
	// CREATE
	@PostMapping("/location")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LocationData createLocation(@RequestBody LocationData locationData) {
		log.info("Creating location {}", locationData);
		
		return rescueService.saveLocation(locationData);
	}
	
	// UPDATE by ID
	@PutMapping("/location/{locationId}")
	public LocationData updateLocation(@PathVariable Long locationId, @RequestBody LocationData locationData) {
		locationData.setLocationId(locationId); // take ID and put into locationData obj so it's in param list and not JSON
		log.info("Updating location {}", locationData);
		return rescueService.saveLocation(locationData);
	}
	
	// READ by ID
	@GetMapping("/location/{locationId}")
	public LocationData retrieveLocation(@PathVariable Long locationId) {
		log.info("Retrieving location with ID = {}", locationId);
		return rescueService.retrieveLocationId(locationId);
	}
	
	// READ all
	@GetMapping("/location")
	public List<LocationData> retrieveAllLocations(){
		log.info("Retrieve all locations.");
		return rescueService.retrieveAllLocations();
	}
	
	// DELETE 
	@DeleteMapping("/location/{locationId}")
	public Map<String, String> deleteLocation(@PathVariable Long locationId){
		log.info("Deleting Location with ID ="+locationId);
		rescueService.deleteLocation(locationId);
		return Map.of("message", "Location with ID=" + locationId + " was deleted succesfully.");
	}
}
