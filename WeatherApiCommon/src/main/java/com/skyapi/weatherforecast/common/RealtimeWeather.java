package com.skyapi.weatherforecast.common;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;

import java.util.Date;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "realtime_weather")

public class RealtimeWeather {
    @Id
    @Column(name = "location_code")
    private String locationCode;

    private int temperature;

    private int humidity;

    private int precipitation;

    private int windSpeed;

    @Column(length = 50)
    private String status;

    private Date lastUpdated;

    @OneToOne
    @JoinColumn(name = "location_code")
//    @MapsId
    private Location location;

}
