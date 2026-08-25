package org.example;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;
import java.time.ZoneId;

public class Main {
    void main() throws Exception {

        Scanner scanner = new Scanner(System.in);


        String area;

        while (true) {
            System.out.print("Välj elområde (SE1, SE2, SE3, SE4): ");
            area = scanner.nextLine().toUpperCase();

            if (area.equals("SE1") || area.equals("SE2") ||
            area.equals("SE3") || area.equals("SE4")) {
                break;
            }

            System.out.println("Ogiltigt elområde. Välj ett av alternativen.");
        }

        LocalDate today = LocalDate.now(ZoneId.of("Europe/Stockholm"));
        IO.println("Datum: " + today);
        String year = String.valueOf(today.getYear());
        String month = String.format(Locale.ROOT,"%02d", today.getMonthValue());
        String day = String.format(Locale.ROOT,"%02d", today.getDayOfMonth());

        ApiClient apiClient = new ApiClient();
        String url = "https://www.elprisetjustnu.se/api/v1/prices/"
                + year + "/" + month + "-" + day + "_" + area + ".json";
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
