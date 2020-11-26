package pl.switalla.co2sensors.sensor.status.state;

import pl.switalla.co2sensors.sensor.model.Sensor;
import pl.switalla.co2sensors.sensor.status.Status;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Container class for {@link StatusState} implementations
 */
public class StatusStates {

    final Map<Status, StatusState> states;

    public StatusStates(Collection<StatusState> states) {
        this.states = states.stream()
            .collect(Collectors.toMap(StatusState::getBaseStatus, Function.identity()));
    }

    public Status determineNewStatus(Sensor sensor, int currentCO2, Collection<Integer> oldCO2) {
        if (!states.containsKey(sensor.getStatus())) {
            throw new IllegalStateException("No status state handler available for status: " + sensor.getStatus());
        }

        return states.get(sensor.getStatus()).determineNewStatus(currentCO2, oldCO2);
    }
}
