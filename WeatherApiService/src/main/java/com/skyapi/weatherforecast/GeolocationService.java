package com.skyapi.weatherforecast;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.exception.GeolocationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class GeolocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeolocationService.class);

    private IP2Location ip2Location = new IP2Location();
    private String DBPath = "ip2locationdb/IP2LOCATION-LITE-DB3.BIN";

    public GeolocationService() {
        try {
            ip2Location.Open(DBPath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Location getLocation(String ipAddress) throws GeolocationException {
        try {
            IPResult result = ip2Location.IPQuery(ipAddress);
            if(!"OK".equals(result.getStatus())){
                throw new GeolocationException("Geolocation failed with status: "+result.getStatus());
            }
            return new Location(result.getCity(), result.getRegion(), result.getCountryLong(), result.getCountryShort());

        } catch (IOException | GeolocationException e) {
            throw new GeolocationException("Error querying IP database", e);
        }
    }
}
