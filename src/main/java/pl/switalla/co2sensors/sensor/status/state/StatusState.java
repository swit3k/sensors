package pl.switalla.co2sensors.sensor.status.state;

import pl.switalla.co2sensors.sensor.status.Status;

import java.util.Collection;

public interface StatusState {

    Status getBaseStatus();
    Status determineNewStatus(int currentCO2, Collection<Integer> oldCO2);
}
