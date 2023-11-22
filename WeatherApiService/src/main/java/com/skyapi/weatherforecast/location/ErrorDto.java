package com.skyapi.weatherforecast.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDto {
    private Date timeStamp;
    private int status;
    private String path;
    private  List<String> errors = new ArrayList<>();

    public void addError(String message){
        this.errors.add(message);
    }
}
