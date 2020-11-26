package pl.switalla.co2sensors.sensor.status.state;

import org.junit.jupiter.api.Test;
import pl.switalla.co2sensors.sensor.model.Sensor;
import pl.switalla.co2sensors.sensor.status.Status;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusStatesTest {

    @Test
    void shouldThrowExceptionIfNoStatusStateHandlerIsAvailable() {
        assertThrows(IllegalStateException.class, () -> {
            StatusStates statusStates = new StatusStates(Collections.singletonList(new AlertStatusState()));
            statusStates.determineNewStatus(new Sensor("sensor-id", Status.OK), 1, Collections.singleton(2));
        }, "No status state handler available for status: OK");
    }

    @Test
    void shouldUseRegisteredStatusState() {
        // arrange
        Sensor sensor = new Sensor("sensor-id", Status.ALERT);
        StatusStates statusStates = new StatusStates(Collections.singletonList(new AlertStatusState()));

        // act
        Status status = statusStates.determineNewStatus(sensor, 1, Collections.singleton(2));

        // assert
        assertThat(status).isNotNull();
    }
}