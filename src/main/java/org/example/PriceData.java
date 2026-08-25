package org.example;

public record PriceData(
        double SEK_per_kWh,
        double EUR_per_kWh,
        double EXR,
        String time_start,
        String time_end
) {
}
