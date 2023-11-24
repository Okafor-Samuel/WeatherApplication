package com.skyapi.weatherforecast.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.annotation.processing.Generated;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "locations")
public class Location {
    @Id
    @Column(length = 12, nullable = false, unique = true)
    @NotNull(message = "Location code cannot be null")
    private String code;
    @Column(length = 128, nullable = false)
    @JsonProperty("city_name")
    @NotBlank(message = "City name cannot be blank")
    private String cityName;
    @Column(length = 128)
    @JsonProperty("region_name")
    private String regionName;
    @Column(length = 64, nullable = false)
    @JsonProperty("country_name")
    @NotBlank(message = "Country name cannot be blank")
    private String countryName;
    @Column(length = 4, nullable = false)
    @JsonProperty("country_code")
    @NotBlank(message = "Country code cannot be blank")
    private String countryCode;
    private boolean enabled;
    @JsonIgnore
    private boolean trashed;
}
