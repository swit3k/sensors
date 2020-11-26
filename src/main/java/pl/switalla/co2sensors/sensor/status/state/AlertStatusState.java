package pl.switalla.co2sensors.sensor.status.state;

import org.springframework.stereotype.Component;
import pl.switalla.co2sensors.sensor.status.Status;

import java.util.Collection;

import static pl.switalla.co2sensors.utils.MeasurementUtils.combined;

/**
 * Class handling Sensor's status changes if
 * the current status is ALERT
 */

@Component
public class AlertStatusState implements StatusState {

    @Override
    public Status getBaseStatus() {
        return Status.ALERT;
    }

    @Override
    public Status determineNewStatus(int currentCO2, Collection<Integer> oldCO2) {
        if (oldCO2.size() >= 2 && combined(currentCO2, oldCO2).allMatch(measurement -> measurement <= 2000)) {
            return Status.OK;
        }
        return Status.ALERT;
    }
}
