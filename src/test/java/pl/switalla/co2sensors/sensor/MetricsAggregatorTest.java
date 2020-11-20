package pl.switalla.co2sensors.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackageClasses = { MetricsAggregator.class })
class MetricsAggregatorTest {

    private  static final String SENSOR_ID = UUID.randomUUID().toString();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MetricsAggregator aggregator;

    @BeforeEach
    void setUp() {
        mongoTemplate.save(MeasurementsCollectorTest.createMeasurement(SENSOR_ID, 1200), "measurement");
        mongoTemplate.save(MeasurementsCollectorTest.createMeasurement(SENSOR_ID, 2010), "measurement");
        mongoTemplate.save(MeasurementsCollectorTest.createMeasurement(SENSOR_ID, 1900), "measurement");
    }

    @Test
    public void shouldCalculateMaxCO2FromLast30Days() {
        Integer max = aggregator.getMaxCO2FromLast30Days(SENSOR_ID);

        assertThat(max).isEqualTo(2010);
    }

    @Test
    public void shouldCalculateAvgCO2FromLast30Days() {
        Integer max = aggregator.getAverageCO2FromLast30Days(SENSOR_ID);

        assertThat(max).isEqualTo((1200 + 2010 + 1900) / 3);
    }
}