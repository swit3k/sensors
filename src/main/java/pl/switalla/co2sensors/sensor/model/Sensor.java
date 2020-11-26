package pl.switalla.co2sensors.sensor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import pl.switalla.co2sensors.sensor.status.Status;

/**
 * Model for Sensor
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {

    @Id
    @JsonIgnore
    String id;

    Status status = Status.OK;

    public Sensor(String id) {
        this.id = id;
    }
}
