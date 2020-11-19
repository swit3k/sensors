package pl.switalla.co2sensors.sensor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Collection;
import java.util.stream.IntStream;

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

    public void determineAndSetStatus(int currentCO2, Collection<Integer> oldCO2) {
        if (currentCO2 >= 2000) {
            status = Status.WARN;
        }

        if (!oldCO2.isEmpty() && combined(currentCO2, oldCO2).allMatch(measurement -> measurement > 2000)) {
            status = Status.ALERT;
        } else if (!oldCO2.isEmpty() && combined(currentCO2, oldCO2).allMatch(measurement -> measurement < 2000)) {
            status = Status.OK;
        }
    }

    public enum Status {
        OK, WARN, ALERT;
    }

    private IntStream combined(int newMeasurement, Collection<Integer> oldMeasurements) {
        return IntStream
            .concat(IntStream.of(newMeasurement), oldMeasurements.stream().mapToInt(Integer::intValue));
    }
}
