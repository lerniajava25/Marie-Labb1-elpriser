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

        for (PriceData price : prices) {
            if (price.SEK_per_kWh() > max) {
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

    public PriceData[] sortPrices(PriceData[] prices) {
        PriceData[] sortedPrices = prices.clone();

        Arrays.sort(sortedPrices,
                Comparator.comparingDouble(PriceData::SEK_per_kWh));

        return sortedPrices;
    }

    public PriceData[] bestChargingTime(PriceData[] prices) {
        if (prices.length < 16) {
            throw new IllegalArgumentException("Det måste finnas minst 16 priser.");
        }

        int windowSize = 16;

        double currentSum = 0;

        for (int i = 0; i < windowSize; i++) {
            currentSum += prices[i].SEK_per_kWh();
        }

        double lowestSum = currentSum;
        int bestStartIndex = 0;

        for (int i = windowSize; i < prices.length; i++) {
            currentSum += prices[i].SEK_per_kWh();
            currentSum -= prices[i - windowSize].SEK_per_kWh();

            if (currentSum < lowestSum) {
                lowestSum = currentSum;
                bestStartIndex = i - windowSize + 1;
            }
        }

        PriceData[] bestPrices = new PriceData[windowSize];

        System.arraycopy(
                prices,
                bestStartIndex,
                bestPrices,
                0,
                windowSize
        );

        return  bestPrices;
    }
}
