package com.skyapi.weatherforecast.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 12, nullable = false, unique = true)
    private String code;
    @Column(length = 128, nullable = false)
    private String cityName;
    @Column(length = 128)
    private String regionName;
    @Column(length = 64, nullable = false)
    private String countryName;
    @Column(length = 4, nullable = false)
    private String countryCode;
    private boolean enabled;
    private boolean trashed;
}
