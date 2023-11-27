package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location add(Location location){
        return locationRepository.save(location);
    }

    public List<Location> list(){
        return locationRepository.findUntrashed();
    }
    public Location get(String code){
        return locationRepository.findByCode(code);
    }
    public Location update(Location locationInRequest) throws LocationNotFoundException {
        String code = locationInRequest.getCode();
        Location locationInDB = locationRepository.findByCode(code);
        if(locationInDB == null){
            throw new LocationNotFoundException("No location found with the given code "+code);
        }
        locationInDB.setCityName(locationInRequest.getCityName());
        locationInDB.setRegionName(locationInRequest.getRegionName());
        locationInDB.setCountryCode(locationInRequest.getCountryCode());
        locationInDB.setCountryName(locationInRequest.getCountryName());
        locationInDB.setEnabled(locationInRequest.isEnabled());

        return locationRepository.save(locationInDB);
    }

    public void delete(String code) throws LocationNotFoundException {
       Location location = locationRepository.findByCode(code);
        if(location == null){
            throw new LocationNotFoundException("No location found with the given code "+code);
        }
        locationRepository.trashedByCode(code);
    }

}
