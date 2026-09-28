package com.example.urban_air_quality.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.AirQualityReading;
import com.example.urban_air_quality.model.MonitoringStation;
import com.example.urban_air_quality.repository.AirQualityReadingRepository;
import com.example.urban_air_quality.repository.MonitoringStationRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class OpenMeteoService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final MonitoringStationRepository stationRepository;
    private final AirQualityReadingRepository readingRepository;

    public OpenMeteoService(
            MonitoringStationRepository stationRepository,
            AirQualityReadingRepository readingRepository) {

        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.stationRepository = stationRepository;
        this.readingRepository = readingRepository;
    }

    public AirQualityReading getLiveAirQuality(String city) {

        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be empty");
        }

        try {
            String normalizedCity = city.trim();
            String encodedCity = URLEncoder.encode(
                    normalizedCity,
                    StandardCharsets.UTF_8
            );

            String geocodingUrl =
                    "https://geocoding-api.open-meteo.com/v1/search"
                            + "?name=" + encodedCity
                            + "&count=1"
                            + "&language=en"
                            + "&format=json";

            HttpRequest geocodingRequest = HttpRequest.newBuilder()
                    .uri(URI.create(geocodingUrl))
                    .timeout(Duration.ofSeconds(20))
                    .GET()
                    .build();

            HttpResponse<String> geocodingResponse =
                    httpClient.send(
                            geocodingRequest,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (geocodingResponse.statusCode() != 200) {
                throw new RuntimeException(
                        "Open-Meteo geocoding failed (HTTP "
                                + geocodingResponse.statusCode() + "): "
                                + responseDetail(geocodingResponse.body())
                );
            }

            JsonNode geocodingJson =
                    objectMapper.readTree(geocodingResponse.body());

            JsonNode results = geocodingJson.get("results");

            if (results == null || results.isEmpty()) {
                throw new IllegalArgumentException("City not found: " + city);
            }

            JsonNode location = results.get(0);
            double latitude = requiredNumber(location, "latitude", "geocoding");
            double longitude = requiredNumber(location, "longitude", "geocoding");

            String airQualityUrl =
                    "https://air-quality-api.open-meteo.com/v1/air-quality"
                            + "?latitude=" + latitude
                            + "&longitude=" + longitude
                            + "&current="
                            + "pm2_5,"
                            + "pm10,"
                            + "carbon_monoxide,"
                            + "nitrogen_dioxide,"
                            + "sulphur_dioxide,"
                            + "ozone,"
                            + "us_aqi"
                            + "&timezone=auto";

            HttpRequest airQualityRequest = HttpRequest.newBuilder()
                    .uri(URI.create(airQualityUrl))
                    .timeout(Duration.ofSeconds(20))
                    .GET()
                    .build();

            HttpResponse<String> airQualityResponse =
                    httpClient.send(
                            airQualityRequest,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (airQualityResponse.statusCode() != 200) {
                throw new RuntimeException(
                        "Open-Meteo air-quality request failed (HTTP "
                                + airQualityResponse.statusCode() + "): "
                                + responseDetail(airQualityResponse.body())
                );
            }

            JsonNode airQualityJson =
                    objectMapper.readTree(airQualityResponse.body());

            JsonNode current =
                    airQualityJson.get("current");

            if (current == null) {
                throw new RuntimeException(
                        "Current air quality data was not returned."
                );
            }

            double pm25 = requiredNumber(current, "pm2_5", "air-quality current data");
            double pm10 = requiredNumber(current, "pm10", "air-quality current data");
            double carbonMonoxide =
                    requiredNumber(current, "carbon_monoxide", "air-quality current data");
            double nitrogenDioxide =
                    requiredNumber(current, "nitrogen_dioxide", "air-quality current data");
            double sulphurDioxide =
                    requiredNumber(current, "sulphur_dioxide", "air-quality current data");
            double ozone =
                    requiredNumber(current, "ozone", "air-quality current data");
            double aqi =
                    requiredNumber(current, "us_aqi", "air-quality current data");

            String airQualityStatus =
                    determineAirQualityStatus(aqi);

            String timeText = current.has("time")
                    ? current.get("time").asText()
                    : null;

            if (timeText == null || timeText.isBlank()) {
                throw new RuntimeException(
                        "Open-Meteo current air-quality response is missing 'time'."
                );
            }
            LocalDateTime recordedAt = LocalDateTime.parse(timeText);

            AirQualityReading reading = new AirQualityReading(
                    city.trim(),
                    latitude,
                    longitude,
                    pm25,
                    pm10,
                    carbonMonoxide,
                    nitrogenDioxide,
                    sulphurDioxide,
                    ozone,
                    aqi,
                    airQualityStatus,
                    recordedAt
            );

            // Reuse an existing station, or create one on the first lookup.
            MonitoringStation station = stationRepository
                    .findFirstByCityIgnoreCase(normalizedCity)
                    .orElseGet(() -> stationRepository.save(
                            new MonitoringStation(
                                    normalizedCity + " Air Monitoring Station",
                                    normalizedCity,
                                    normalizedCity,
                                    "ACTIVE"
                            )
                    ));

            // Link reading to monitoring station
            reading.setMonitoringStation(station);

            // Save reading into MySQL
            return readingRepository.save(reading);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not complete the Open-Meteo request: "
                            + e.getMessage(),
                    e
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "Open-Meteo API request was interrupted.",
                    e
            );
        }
    }

    private double requiredNumber(
            JsonNode node,
            String fieldName,
            String source) {

        JsonNode value = node.get(fieldName);

        if (value == null || value.isNull() || !value.isNumber()) {
            throw new RuntimeException(
                    "Open-Meteo response is missing numeric field '"
                            + fieldName + "' in " + source + "."
            );
        }

        return value.asDouble();
    }

    private String responseDetail(String responseBody) {
        if (responseBody == null || responseBody.isBlank()) {
            return "The upstream service returned an empty response.";
        }

        String detail = responseBody.trim();
        return detail.length() > 500 ? detail.substring(0, 500) : detail;
    }

    private String determineAirQualityStatus(double aqi) {

        if (aqi <= 50) {
            return "GOOD";
        } else if (aqi <= 100) {
            return "MODERATE";
        } else if (aqi <= 150) {
            return "UNHEALTHY FOR SENSITIVE GROUPS";
        } else if (aqi <= 200) {
            return "UNHEALTHY";
        } else if (aqi <= 300) {
            return "VERY UNHEALTHY";
        } else {
            return "HAZARDOUS";
        }
    }
}
