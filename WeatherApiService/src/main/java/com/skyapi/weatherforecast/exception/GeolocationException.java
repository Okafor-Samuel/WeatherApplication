package com.skyapi.weatherforecast.exception;

public class GeolocationException extends Throwable {
    public GeolocationException(String message) {
        super(message);
    }

    public GeolocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
