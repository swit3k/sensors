package pl.switalla.co2sensors.utils;

import java.util.Collection;
import java.util.stream.IntStream;

public class MeasurementUtils {
    public static IntStream combined(int newMeasurement, Collection<Integer> oldMeasurements) {
        return IntStream
            .concat(IntStream.of(newMeasurement), oldMeasurements.stream().mapToInt(Integer::intValue));
    }
}
