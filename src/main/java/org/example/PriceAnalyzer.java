package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class PriceAnalyzer {

    public double minPrice(PriceData[] prices) {
        if (prices.length == 0) {
            throw new IllegalArgumentException("Prislistan får inte vara tom");
        }
        double min = prices[0].SEK_per_kWh();

        for (PriceData price : prices) {
            if (price.SEK_per_kWh() < min) {
                min = price.SEK_per_kWh();
            }
        }

        return min;
    }

    public double maxPrice(PriceData[] prices) {
        if (prices.length == 0) {
            throw new IllegalArgumentException("Prislistan får inte vara tom");
        }
        double max = prices[0].SEK_per_kWh();

        for (PriceData price : prices){
            if (price.SEK_per_kWh() > max){
                max = price.SEK_per_kWh();
            }
        }

        return max;
    }

    public double averagePrice(PriceData[] prices) {
        if (prices.length == 0) {
            throw new IllegalArgumentException("Prislistan får inte vara tom");
        }
        double sum = 0;

        for (PriceData price : prices) {
            sum += price.SEK_per_kWh();
        }

        return sum / prices.length;
    }

    public PriceData [] sortPrices(PriceData[] prices) {
        PriceData[] sortedPrices = prices.clone();

        Arrays.sort(sortedPrices,
        Comparator.comparingDouble(PriceData::SEK_per_kWh));

        return sortedPrices;
    }
}
