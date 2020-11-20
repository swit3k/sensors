package pl.switalla.co2sensors.sensor;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import pl.switalla.co2sensors.sensor.model.Measurement;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Class that collects metrics for given sensorId
 */

@Component
public class MetricsAggregator {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsAggregator.class);

    private final MeasurementsRepository measurementsRepository;
    private final MongoTemplate mongoTemplate;

    public MetricsAggregator(MeasurementsRepository measurementsRepository, MongoTemplate mongoTemplate) {
        this.measurementsRepository = measurementsRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Integer getMaxCO2FromLast30Days(String sensorId) {
        Instant last30Days = Instant.now().minus(30, ChronoUnit.DAYS);
        Sort sort = Sort.by("co2").descending();

        return this.measurementsRepository.findFirstBySensorIdAndTimeAfter(sensorId, last30Days, sort)
            .map(Measurement::getCo2)
            .orElse(0);
    }

    public Integer getAverageCO2FromLast30Days(String sensorId) {
        Instant last30Days = Instant.now().minus(30, ChronoUnit.DAYS);

        SortOperation sort = sort(Sort.by("co2").descending());
        GroupOperation avgCo2 = group("sensorId").avg("co2").as("avgCo2");

        MatchOperation matchingSensorId = match(new Criteria("sensorId").is(sensorId));
        MatchOperation matchingTime = match(new Criteria("time").gte(last30Days));

        Aggregation aggregation = newAggregation(matchingSensorId, matchingTime, sort, avgCo2, limit(1));
        Document aggregationResult = mongoTemplate.aggregate(aggregation, "measurement", Document.class)
            .getUniqueMappedResult();

        return Optional.ofNullable(aggregationResult)
            .map(document -> document.getDouble("avgCo2").intValue())
            .orElse(0);
    }
}
