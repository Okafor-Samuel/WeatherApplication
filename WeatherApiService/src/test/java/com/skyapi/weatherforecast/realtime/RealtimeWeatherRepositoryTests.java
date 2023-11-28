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
        realtimeWeather.setTemperature(-2);
        realtimeWeather.setHumidity(40);
        realtimeWeather.setPrecipitation(10);
        realtimeWeather.setStatus("Rainy");
        realtimeWeather.setWindSpeed(35);
        realtimeWeather.setLastUpdated(new Date());

       RealtimeWeather updatedRealtimeWeather = weatherRepository.save(realtimeWeather);

       assertThat(updatedRealtimeWeather.getHumidity()).isEqualTo(40);
    }
}
