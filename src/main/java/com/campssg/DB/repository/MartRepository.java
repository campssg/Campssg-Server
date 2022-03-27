package com.campssg.DB.repository;

import com.campssg.DB.entity.Mart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MartRepository extends JpaRepository<Mart, Long> {

    List<Mart> findByUser_userId(Long userId);

    @Query(value = "SELECT * ,\n"
        + "       ( 6371 * ACOS( COS( RADIANS( :lat ) )\n"
        + "                        * COS( RADIANS( latitude ) )\n"
        + "                        * COS( RADIANS( longitude )\n"
        + "                        - RADIANS( :lon ) ) + SIN( RADIANS( :lat ) ) * SIN( RADIANS( latitude ) ) ) )\n"
        + "       AS distance\n"
        + "FROM mart having distance <= 3 order by distance", nativeQuery = true)
    List<Mart> findAroundMart(@Param("lat") Long lat, @Param("lon") Long lon);
}
