package pl.switalla.co2sensors.sensor;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.switalla.co2sensors.sensor.model.Measurement;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Measurements Repository
 */

public interface MeasurementsRepository extends MongoRepository<Measurement, String> {

    List<Measurement> findFirst2BySensorId(String sensorId, Sort sort);

    Optional<Measurement> findFirstBySensorIdAndTimeAfter(String sensorId, Instant time, Sort sort);
}
