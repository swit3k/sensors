package pl.switalla.co2sensors.sensor;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.switalla.co2sensors.sensor.model.Measurement;

/**
 * Measurements Repository
 */

public interface MeasurementsRepository extends MongoRepository<Measurement, String> {

}
