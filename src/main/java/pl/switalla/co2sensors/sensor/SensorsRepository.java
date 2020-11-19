package pl.switalla.co2sensors.sensor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.switalla.co2sensors.sensor.model.Sensor;

import java.time.LocalDateTime;

/**
 * Sensors Repository
 */

public interface SensorsRepository extends MongoRepository<Sensor, String> {

}
