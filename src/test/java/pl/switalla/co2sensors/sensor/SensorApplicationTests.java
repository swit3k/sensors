package pl.switalla.co2sensors.sensor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.switalla.co2sensors.config.error.HttpError;
import pl.switalla.co2sensors.sensor.http.request.MeasurementBody;
import pl.switalla.co2sensors.sensor.model.Sensor;
import pl.switalla.co2sensors.sensor.status.Status;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SensorApplicationTests {

    private static final String SENSOR_ID = UUID.randomUUID().toString();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorsRepository sensorsRepository;

    @BeforeEach
    void setUp() {
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldPerformPostOnMeasurementsAndReceive201() throws Exception {
        // arrange
        MeasurementBody measurementBody = new MeasurementBody(2000, Instant.now());

        String json = objectMapper.writeValueAsString(measurementBody);

        // act & assert
        this.mockMvc
            .perform(
                post("/api/v1/sensors/{sensorId}/measurements", SENSOR_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnSensorStatusOnGetGivenSensorId() throws Exception {
        // arrange
        Sensor sensorEntity = new Sensor(SENSOR_ID, Status.OK);
        when(sensorsRepository.findById(ArgumentMatchers.eq(SENSOR_ID)))
            .thenReturn(Optional.of(sensorEntity));

        // act
        MvcResult mvcResult = this.mockMvc
            .perform(get("/api/v1/sensors/{sensorId}", SENSOR_ID))
            .andExpect(status().isOk())
            .andReturn();

        Sensor sensor = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Sensor.class);

        // assert
        assertThat(sensor.getStatus()).isEqualTo(Status.OK);
    }

    @Test
    void shouldReturn404GivenInvalidSensorId() throws Exception {
        this.mockMvc
            .perform(get("/api/v1/sensors/{sensorId}", UUID.randomUUID()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestGivenInvalidUUID() throws Exception {
        // act
        MvcResult mvcResult = this.mockMvc
            .perform(get("/api/v1/sensors/{sensorId}", 1234))
            .andExpect(status().isBadRequest())
            .andReturn();

        HttpError httpError = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), HttpError.class);

        // assert
        assertThat(httpError.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
