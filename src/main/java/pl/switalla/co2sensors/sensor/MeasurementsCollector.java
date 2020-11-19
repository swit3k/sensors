package pl.switalla.co2sensors.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pl.switalla.co2sensors.sensor.model.Measurement;
import pl.switalla.co2sensors.sensor.model.Sensor;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeasurementsCollector {

    private static final Logger LOG = LoggerFactory.getLogger(MeasurementsCollector.class);

    private final SensorsRepository sensorsRepository;
    private final MeasurementsRepository measurementsRepository;

    public MeasurementsCollector(SensorsRepository sensorsRepository, MeasurementsRepository measurementsRepository) {
        this.sensorsRepository = sensorsRepository;
        this.measurementsRepository = measurementsRepository;
    }

    public void collect(String sensorId, Measurement measurement) {
        Sensor sensor = sensorsRepository.findById(sensorId)
            .orElse(new Sensor(sensorId));

        Sort sort = Sort.by("time").descending();
        List<Integer> lastTwoMeasurements = measurementsRepository.findFirst2BySensorId(sensorId, sort).stream()
            .map(Measurement::getCo2)
            .collect(Collectors.toList());

        measurementsRepository.save(measurement);

        sensor.determineAndSetStatus(measurement.getCo2(), lastTwoMeasurements);

        LOG.debug("Status of sensor: {}, set to: {}", sensorId, sensor.getStatus());

        sensorsRepository.save(sensor);
    }
}
