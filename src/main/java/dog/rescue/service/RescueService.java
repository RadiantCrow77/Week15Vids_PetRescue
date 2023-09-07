package dog.rescue.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dog.rescue.controller.model.LocationData;
import dog.rescue.dao.LocationDao;
import dog.rescue.entity.Location;


@Service
public class RescueService {
	@Autowired
	private LocationDao locationDao;

	@Transactional(readOnly = false)
	public LocationData saveLocation(LocationData locationData) {
	Location location = locationData.toLocation();
	Location dbLocation = locationDao.save(location);
	
	return new LocationData(dbLocation);
	}

	@Transactional(readOnly = true)
	public LocationData retrieveLocationId(Long locationId) {
		Location location = findLocationById(locationId);
		return new LocationData(location);
	}

	private Location findLocationById(Long locationId) {
		// TODO Auto-generated method stub
		return locationDao.findById(locationId).orElseThrow(() -> new NoSuchElementException("Location with ID ="+locationId+" was not found.")); // find by Id returns an optional, so need to call or else throw
	}

	@Transactional(readOnly = true)
	public List<LocationData> retrieveAllLocations() {
		// @formatter:off
		return locationDao.findAll() // turn list of location entities ...
				.stream() // into a stream ...
				.sorted((loc1,loc2) -> 
				loc1.getBusinessName().compareTo(loc2.getBusinessName())) // sort method, sort on location 1
//				.map(loc -> new LocationData(loc)) // then into location data objects; loc = entity -> return new Location Data (entity) .. or could do:
				 .map(LocationData::new) // Java: calling constructor on location data
				.toList(); 
		// @formatter:on
	}

	@Transactional(readOnly = false)
	public void deleteLocation(Long locationId) {
		Location location = findLocationById(locationId);
		locationDao.delete(location);
		
	}

}
