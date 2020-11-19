package pl.switalla.co2sensors.sensor.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.switalla.co2sensors.sensor.MeasurementsRepository;
import pl.switalla.co2sensors.sensor.SensorsRepository;
import pl.switalla.co2sensors.sensor.http.exception.SensorNotFoundException;
import pl.switalla.co2sensors.sensor.http.request.MeasurementBody;
import pl.switalla.co2sensors.sensor.http.response.MetricsBody;
import pl.switalla.co2sensors.sensor.model.Measurement;
import pl.switalla.co2sensors.sensor.model.Sensor;

import java.util.List;
import java.util.UUID;

/**
 * Controller exposing Sensor's API
 */

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorsController {

    private static final Logger LOG = LoggerFactory.getLogger(SensorsController.class);

    private final SensorsRepository sensorsRepository;
    private final MeasurementsRepository measurementsRepository;

    public SensorsController(SensorsRepository sensorsRepository, MeasurementsRepository measurementsRepository) {
        this.sensorsRepository = sensorsRepository;
        this.measurementsRepository = measurementsRepository;
    }

    @PostMapping("/{sensorId}/measurements")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectMeasurement(@PathVariable String sensorId, @Validated @RequestBody MeasurementBody measurementBody) {
        LOG.debug("Collecting measurement for sensorId: {}, co2: {} ppm", sensorId, measurementBody.getCo2());

        measurementsRepository.save(MeasurementBody.toModel(sensorId, measurementBody));
    }

    @GetMapping("/{sensorId}")
    public Sensor sensor(@PathVariable UUID sensorId) {
        return sensorsRepository.findById(sensorId.toString())
            .orElseThrow(SensorNotFoundException::new);
    }

    @GetMapping
    public List<Sensor> sensors() {
        return sensorsRepository.findAll();
    }

    @GetMapping("/measurements")
    public List<Measurement> measurements() {
        return measurementsRepository.findAll();
    }

    @GetMapping("/{sensorId}/metrics")
    public MetricsBody sensorMetrics(@PathVariable String sensorId) {
        return null;
    }
}
