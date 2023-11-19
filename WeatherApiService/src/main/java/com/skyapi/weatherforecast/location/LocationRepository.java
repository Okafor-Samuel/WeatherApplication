package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, String> {
    @Query("SELECT l from Location l where l.trashed= false")
    List<Location> findUntrashed();

    @Query("SELECT l from Location l where l.trashed= false AND l.code=?1")
    Location findByCode(String code);

    @Modifying
    @Query("UPDATE Location SET trashed = true WHERE code=?1")
    public void trashedByCode(String code);
}
