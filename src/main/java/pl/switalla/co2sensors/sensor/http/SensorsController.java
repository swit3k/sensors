package pl.switalla.co2sensors.sensor.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.switalla.co2sensors.sensor.MeasurementsCollector;
import pl.switalla.co2sensors.sensor.MetricsAggregator;
import pl.switalla.co2sensors.sensor.SensorsRepository;
import pl.switalla.co2sensors.sensor.http.exception.SensorNotFoundException;
import pl.switalla.co2sensors.sensor.http.request.MeasurementBody;
import pl.switalla.co2sensors.sensor.http.response.MetricsBody;
import pl.switalla.co2sensors.sensor.model.Sensor;

import java.util.UUID;

/**
 * Controller exposing Sensor's API
 */

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorsController {

    private static final Logger LOG = LoggerFactory.getLogger(SensorsController.class);

    private final SensorsRepository sensorsRepository;
    private final MeasurementsCollector measurementsCollector;
    private final MetricsAggregator metricsAggregator;

    public SensorsController(SensorsRepository sensorsRepository, MeasurementsCollector measurementsCollector, MetricsAggregator metricsAggregator) {
        this.sensorsRepository = sensorsRepository;
        this.measurementsCollector = measurementsCollector;
        this.metricsAggregator = metricsAggregator;
    }

    @PostMapping("/{sensorId}/measurements")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectMeasurement(@PathVariable String sensorId, @Validated @RequestBody MeasurementBody measurementBody) {
        LOG.debug("Collecting measurement for sensorId: {}, co2: {} ppm", sensorId, measurementBody.getCo2());

        measurementsCollector.collect(sensorId, MeasurementBody.toModel(sensorId, measurementBody));
    }

    @GetMapping("/{sensorId}")
    public Sensor sensor(@PathVariable UUID sensorId) {
        return sensorsRepository.findById(sensorId.toString())
            .orElseThrow(SensorNotFoundException::new);
    }

    @GetMapping("/{sensorId}/metrics")
    public MetricsBody sensorMetrics(@PathVariable String sensorId) {
        return new MetricsBody(
            metricsAggregator.getMaxCO2FromLast30Days(sensorId),
            metricsAggregator.getAverageCO2FromLast30Days(sensorId)
        );
    }
}
