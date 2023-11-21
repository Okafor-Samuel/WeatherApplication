package com.skyapi.weatherforecast.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDto {
    private Date timeStamp;
    private int status;
    private String path;
    private String error;
}
