package pl.switalla.co2sensors.sensor.http.request;

import lombok.Value;
import pl.switalla.co2sensors.sensor.model.Measurement;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * Representation of sensor's single measurement
 * used on POST /api/v1/sensors/{id}/measurements
 */

@Value
public class MeasurementBody {

    @NotNull
    @Min(1)
    Integer co2;

    @NotNull
    Instant time;

    public static Measurement toModel(String sensorId, MeasurementBody body) {
        return new Measurement(UUID.randomUUID().toString(), sensorId, body.co2, body.time);
    }
}
