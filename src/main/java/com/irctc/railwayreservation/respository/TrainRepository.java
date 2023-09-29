package com.irctc.railwayreservation.respository;

import com.irctc.railwayreservation.entity.TrainEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends CrudRepository<TrainEntity, Long> {

    @Query(value = "SELECT * FROM train_entity WHERE train_number = :trainNumber", nativeQuery = true)
    Optional<TrainEntity> findByTrainNumber(@Param("trainNumber") int trainNumber);

}
