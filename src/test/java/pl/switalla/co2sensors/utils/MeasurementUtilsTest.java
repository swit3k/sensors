package pl.switalla.co2sensors.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class MeasurementUtilsTest {
    @Test
    void shouldCombineInputParametersIntoOneStream() {
        IntStream combinedMeasurements = MeasurementUtils.combined(3000, List.of(1000, 2000));
        assertThat(combinedMeasurements).hasSize(3);
    }
}