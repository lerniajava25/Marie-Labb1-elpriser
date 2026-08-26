package org.example;

public class PriceAnalyzer {

    public double minPrice(PriceData[] prices) {
        double min = prices[0].SEK_per_kWh();

        for (PriceData price : prices) {
            if (price.SEK_per_kWh() < min) {
                min = price.SEK_per_kWh();
            }
        }

        return min;
    }

    public double maxPrice(PriceData[] prices) {
        double max = prices[0].SEK_per_kWh();

        for (PriceData price : prices){
            if (price.SEK_per_kWh() > max){
                max = price.SEK_per_kWh();
            }
        }

        return max;
    }

    public double averagePrice(PriceData[] prices) {
        double sum = 0;

        for (PriceData price : prices) {
            sum += price.SEK_per_kWh();
        }

        return sum / prices.length;
    }
}
