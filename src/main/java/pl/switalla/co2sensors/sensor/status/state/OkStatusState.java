package pl.switalla.co2sensors.sensor.status.state;

import org.springframework.stereotype.Component;
import pl.switalla.co2sensors.sensor.status.Status;

import java.util.Collection;

/**
 * Class handling Sensor's status changes if
 * the current status is OK
 */

@Component
public class OkStatusState implements StatusState {

    @Override
    public Status getBaseStatus() {
        return Status.OK;
    }

    @Override
    public Status determineNewStatus(int currentCO2, Collection<Integer> oldCO2) {
        return currentCO2 > 2000 ? Status.WARN : Status.OK;
    }
}
