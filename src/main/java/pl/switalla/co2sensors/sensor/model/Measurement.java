package pl.switalla.co2sensors.sensor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * Model for Sensor's measurement
 */

@Getter
@AllArgsConstructor
public class Measurement {

    @Id
    String id;
    String sensorId;
    Integer co2;
    Instant time;
}
