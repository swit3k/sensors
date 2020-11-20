package pl.switalla.co2sensors.sensor.http.request;

import org.junit.jupiter.api.Test;
import pl.switalla.co2sensors.sensor.model.Measurement;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class MeasurementBodyTest {

    @Test
    void shouldMapToModel() {
        // arrange
        Instant now = Instant.now();
        MeasurementBody body = new MeasurementBody(2000, now);
        String sensorId = "sensor-id";

        // act
        Measurement model = MeasurementBody.toModel(sensorId, body);

        // assert
        assertThat(model.getCo2()).isEqualTo(body.getCo2());
        assertThat(model.getSensorId()).isEqualTo(sensorId);
        assertThat(model.getTime()).isEqualTo(now);
    }
}