package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/locations")
public class LocationApiController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> addLocation( @RequestBody @Valid  Location location){
     Location addedLocation = locationService.add(location);
     URI uri =URI.create("/v1/locations/"+ addedLocation.getCode());
     return ResponseEntity.created(uri).body(addedLocation);
    }

    @GetMapping
    private ResponseEntity<?> listLocations(){
       List<Location> locations = locationService.list();
       if(locations.isEmpty()){
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.ok().body(locations);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Location> getLocation(@PathVariable("code") String code){
        Location location = locationService.get(code);
        if(location== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(location);
    }

    @PutMapping
      public ResponseEntity<?> updateLocation(@RequestBody @Valid Location location){
          try{
              Location updatedLocation = locationService.update(location);
              return ResponseEntity.ok(updatedLocation);
          }catch (LocationNotFoundException e){
              return ResponseEntity.notFound().build();
          }
        }
}
