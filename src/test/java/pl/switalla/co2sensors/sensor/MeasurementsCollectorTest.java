package pl.switalla.co2sensors.sensor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import pl.switalla.co2sensors.sensor.model.Measurement;
import pl.switalla.co2sensors.sensor.model.Sensor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeasurementsCollectorTest {

    @Mock
    private SensorsRepository sensorsRepository;

    @Mock
    private MeasurementsRepository measurementsRepository;

    @Mock
    private Sensor sensor;

    @InjectMocks
    private MeasurementsCollector collector;

    @Test
    void shouldVerifyIfModelsAreSavedUponMeasurementsCollection() {
        // arrange
        String sensorId = "sensor-id";
        Measurement measurement = createMeasurement(sensorId, 1700);

        when(sensorsRepository.findById(eq(sensorId))).thenReturn(of(sensor));
        when(measurementsRepository.findFirst2BySensorId(eq(sensorId), any(Sort.class)))
            .thenReturn(List.of(createMeasurement(sensorId, 1500)));

        ArgumentCaptor<Integer> newMeasurement = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<List<Integer>> oldMeasurements = ArgumentCaptor.forClass(List.class);

        // act
        collector.collect(sensorId, measurement);

        // assert
        verify(sensor).determineAndSetStatus(newMeasurement.capture(), oldMeasurements.capture());
        verify(measurementsRepository).save(eq(measurement));

        assertThat(newMeasurement.getValue()).isEqualTo(1700);
        assertThat(oldMeasurements.getValue()).hasSize(1);
    }

    public static Measurement createMeasurement(String sensorId, Integer co2) {
        return new Measurement(UUID.randomUUID().toString(), sensorId, co2, Instant.now());
    }
}