package pl.switalla.co2sensors.sensor.status.state;

import org.junit.jupiter.api.Test;
import pl.switalla.co2sensors.sensor.status.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WarnStatusStateTest {

    @Test
    void shouldRemainUnchangedIfNoMoreThanTwoMeasurementsAbove2000() {
        // arrange
        WarnStatusState state = new WarnStatusState();

        // act
        Status status = state.determineNewStatus(2001, List.of(2020, 1900));

        // assert
        assertThat(status).isEqualTo(Status.WARN);
    }

    @Test
    void shouldTurnIntoAlert() {
        // arrange
        WarnStatusState state = new WarnStatusState();

        // act
        Status status = state.determineNewStatus(2050, List.of(2001, 3000));

        // assert
        assertThat(status).isEqualTo(Status.ALERT);
    }

    @Test
    void shouldNotTurnAlertIfNotEnoughMeasurementsWereCollected() {
        // arrange
        WarnStatusState state = new WarnStatusState();

        // act
        Status status = state.determineNewStatus(2050, List.of(2020));

        // assert
        assertThat(status).isEqualTo(Status.WARN);
    }

    @Test
    void shouldTurnIntoOkIfBelow2000() {
        // arrange
        WarnStatusState state = new WarnStatusState();

        // act
        Status status = state.determineNewStatus(1900, List.of(2020, 1700));

        // assert
        assertThat(status).isEqualTo(Status.OK);
    }

}