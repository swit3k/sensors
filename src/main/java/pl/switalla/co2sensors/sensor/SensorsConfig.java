package pl.switalla.co2sensors.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.switalla.co2sensors.sensor.status.state.StatusState;
import pl.switalla.co2sensors.sensor.status.state.StatusStates;

import java.util.List;

@Configuration
public class SensorsConfig {

    @Bean
    @Autowired
    public StatusStates statusResolver(final List<StatusState> states) {
        return new StatusStates(states);
    }
}
