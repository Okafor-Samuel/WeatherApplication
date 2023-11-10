package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)

public class LocationRepositoryTests {
    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testAddSuccess(){
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Location savedLocation = locationRepository.save(location);
        assertThat(savedLocation).isNotNull();
        assertThat(savedLocation.getCode()).isEqualTo("NYC_USA");
    }

     @Test
    public void testListSuccess(){
      List<Location> locationList =  locationRepository.findUntrashed();
      assertThat(locationList).isNotNull();
      locationList.forEach(System.out::println);
     }

     @Test
    public void testGetNotFound(){
        String code ="ABCD";
        Location location = locationRepository.findByCode(code);

        assertThat(location).isNull();
     }

    @Test
    public void testGetFound(){
        String code ="b";
        Location location = locationRepository.findByCode(code);

        assertThat(location).isNotNull();
        assertThat(location.getCode().equals(code));
    }
}
