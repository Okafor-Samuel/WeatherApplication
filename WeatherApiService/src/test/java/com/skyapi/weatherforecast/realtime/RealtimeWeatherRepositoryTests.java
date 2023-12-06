package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.RealtimeWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RealtimeWeatherRepositoryTests {
    @Autowired
    private RealtimeWeatherRepository weatherRepository;

    @Test
    public void testUpdate(){
        String locationCode = "b";

       RealtimeWeather realtimeWeather = weatherRepository.findById(locationCode).get();
        realtimeWeather.setTemperature(0);
        realtimeWeather.setHumidity(10);
        realtimeWeather.setPrecipitation(20);
        realtimeWeather.setStatus("Rainy");
        realtimeWeather.setWindSpeed(30);
        realtimeWeather.setLastUpdated(new Date());

       RealtimeWeather updatedRealtimeWeather = weatherRepository.save(realtimeWeather);

       assertThat(updatedRealtimeWeather.getHumidity()).isEqualTo(10);
    }

    @Test
    public void testFindByCountryCodeAndCityNotFound(){
        String countryCode = "JP";
        String cityName = "Tokyo";

        RealtimeWeather realtimeWeather = weatherRepository.findByCountryCodeAndCity(countryCode, cityName);
        assertThat(realtimeWeather).isNull();
    }

    @Test
    public void testFindByCountryCodeAndCityFound(){
        String countryCode = "NG";
        String cityName = "New York City";

        RealtimeWeather realtimeWeather = weatherRepository.findByCountryCodeAndCity(countryCode, cityName);
        assertThat(realtimeWeather).isNotNull();
        assertThat(realtimeWeather.getLocation().getCityName()).isEqualTo(cityName);
    }
}
