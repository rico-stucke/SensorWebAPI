package de.htw_berlin.sensor_web_api.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This class is used to allow uploading sensor data to the OHDM database.
 * @author Rico Stucke
 *
 */
public class SensorData {

    private long sensorId;
    private String value;
    private long sourceUserId;
    private LocalDateTime validSinceDate;
    private LocalDateTime validUntilDate;
    private long geometryId;
    private String geometryType;

    DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;

    /**
     *
     * @param sensorId the id of the sensor on the database
     * @param value the measured value by the sensor
     * @param sourceUserId Id of the user who measured this data
     * @param validSince start of the time frame for which this data is valid
     * @param validUntil end of the time frame for which this data is valid
     */
    public SensorData(String sensorId, String value,
                      String sourceUserId, String validSince,
                      String validUntil, String geometryId, String geometryType)
    {
        this.sensorId = Long.parseLong(sensorId);
        this.value = value;
        this.sourceUserId = Long.parseLong(sourceUserId);
        this.validSinceDate = LocalDateTime.parse(validSince, format);
        this.validUntilDate = LocalDateTime.parse(validUntil, format);
        this.geometryId = Long.parseLong(geometryId);
        this.geometryType = geometryType;
    }

    public long getSensorId() {
        return this.sensorId;
    }

    public String getValue() {
        return this.value;
    }


    public long getSourceUserId() {
        return this.sourceUserId;
    }


    public LocalDate getValidSince() {
        return this.validSinceDate.toLocalDate();
    }


    public LocalDate getValidUntil() {
        return this.validUntilDate.toLocalDate();
    }

    public Long getGeometryId() {
        return this.geometryId;
    }
    public String getGeometryType() {
        return this.geometryType;
    }
}