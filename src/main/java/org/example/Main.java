package org.example;

public class Main {
    void main() throws Exception {
        ApiClient apiClient = new ApiClient();
        String url = "https://www.elprisetjustnu.se/api/v1/prices/2026/08-24_SE3.json";
        String data = apiClient.fetchData(url);

        PriceDataParser parser = new PriceDataParser();
        PriceData[] prices = parser.parse(data);

        IO.println("Antal priser: " + prices.length);

        if (prices.length == 0) {
            IO.println( "Inga priser hittades");
            return;
        }

        IO.println("Första priset: " + prices[0].SEK_per_kWh());

    }
}
