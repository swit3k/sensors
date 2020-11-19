package pl.switalla.co2sensors.sensor.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response class for Sensor's metrics
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsBody {
    Integer maxLast30Days;
    Integer avgLast30Days;
}
