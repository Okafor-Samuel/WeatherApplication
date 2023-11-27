package com.skyapi.weatherforecast.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 3, max = 12, message = "Location code must have 3 to 12 characters")
    private String code;

    @Column(length = 128, nullable = false)
    @JsonProperty("city_name")
    @NotNull(message = "City name cannot be null")
    @Length(min = 3, max = 120, message = "City name must have 3 to 120 characters")
    private String cityName;

    @Column(length = 128)
    @JsonProperty("region_name")
    @Length(min = 3, max = 120, message = "Region name must have 3 to 120 characters")
    private String regionName;

    @Column(length = 64, nullable = false)
    @JsonProperty("country_name")
    @NotNull(message = "Country name cannot be null")
    @Length(min = 3, max = 64, message = "Country name must have 3 to 64 characters")
    private String countryName;

    @Column(length = 4, nullable = false)
    @JsonProperty("country_code")
    @NotNull (message = "Country code cannot be null")
    @Length(min = 2, max = 2, message = "Country code must have 2 characters")
    private String countryCode;

    private boolean enabled;

    @JsonIgnore
    private boolean trashed;
}
