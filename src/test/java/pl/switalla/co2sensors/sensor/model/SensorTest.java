package pl.switalla.co2sensors.sensor.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SensorTest {

    private static final String SENSOR_ID = "sensor-id";

    @Test
    void shouldSetWarnStatusIfCO2IsAbove2000() {
        Sensor sensor = new Sensor(SENSOR_ID);

        sensor.determineAndSetStatus(2000, List.of(1000, 1500));

        assertThat(sensor.getStatus()).isEqualTo(Sensor.Status.WARN);
    }

    @Test
    void shouldSetWarnStatusIfCO2IsAbove2000AndNoMoreMeasurmentsExist() {
        Sensor sensor = new Sensor(SENSOR_ID);

        sensor.determineAndSetStatus(2000, Collections.emptyList());

        assertThat(sensor.getStatus()).isEqualTo(Sensor.Status.WARN);
    }

    @Test
    void shouldSetOkStatusIfCO2IsBelow2000() {
        Sensor sensor = new Sensor(SENSOR_ID);

        sensor.determineAndSetStatus(1999, List.of(1000, 1500));

        assertThat(sensor.getStatus()).isEqualTo(Sensor.Status.OK);
    }

    @Test
    void shouldSetAlertStatusIfCO2IsAbove2000BasedOnAllMeasurements() {
        Sensor sensor = new Sensor(SENSOR_ID);

        sensor.determineAndSetStatus(2001, List.of(2200, 3000));

        assertThat(sensor.getStatus()).isEqualTo(Sensor.Status.ALERT);
    }
}