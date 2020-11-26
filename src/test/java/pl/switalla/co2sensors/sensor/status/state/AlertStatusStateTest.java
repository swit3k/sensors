package pl.switalla.co2sensors.sensor.status.state;

import org.junit.jupiter.api.Test;
import pl.switalla.co2sensors.sensor.status.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AlertStatusStateTest {

    @Test
    void shouldTurnIntoOk() {
        // arrange
        AlertStatusState state = new AlertStatusState();

        // act
        Status status = state.determineNewStatus(1900, List.of(1900, 1990));

        // assert
        assertThat(status).isEqualTo(Status.OK);
    }

    @Test
    void shouldRemainUnchangedIfLastThreeMeasurementsAreAbove2000() {
        // arrange
        AlertStatusState state = new AlertStatusState();

        // act
        Status status = state.determineNewStatus(2001, List.of(2001, 2001));

        // assert
        assertThat(status).isEqualTo(Status.ALERT);
    }

    @Test
    void shouldRemainUnchangedIfLastThreeMeasurementsAreAbove20001() {
        // arrange
        AlertStatusState state = new AlertStatusState();

        // act
        Status status = state.determineNewStatus(1900, List.of(1990, 2001));

        // assert
        assertThat(status).isEqualTo(Status.ALERT);
    }
}