package pl.switalla.co2sensors.sensor.status.state;

import org.junit.jupiter.api.Test;
import pl.switalla.co2sensors.sensor.status.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OkStatusStateTest {

    @Test
    void shouldTurnIntoWarn() {
        // arrange
        OkStatusState state = new OkStatusState();

        // act
        Status status = state.determineNewStatus(2001, List.of(1700, 1900));

        // assert
        assertThat(status).isEqualTo(Status.WARN);
    }

    @Test
    void shouldRemainUnchangedIfBelow2000() {
        // arrange
        OkStatusState state = new OkStatusState();

        // act
        Status status = state.determineNewStatus(1900, List.of(1800, 1700));

        // assert
        assertThat(status).isEqualTo(Status.OK);
    }
}