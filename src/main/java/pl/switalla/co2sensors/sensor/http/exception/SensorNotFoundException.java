package pl.switalla.co2sensors.sensor.http.exception;

/**
 * Exception representing '404 Not Found' for Sensor
 */

public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException() {
        super("Sensor not found");
    }
}
