package com.skyapi.weatherforecast.location;

public class LocationNotFoundException extends Throwable {
    public LocationNotFoundException(String message) {
        super(message);
    }
}
