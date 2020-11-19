package pl.switalla.co2sensors.sensor;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.switalla.co2sensors.sensor.model.Sensor;

/**
 * Sensors Repository
 */

public interface SensorsRepository extends MongoRepository<Sensor, String> {

}
