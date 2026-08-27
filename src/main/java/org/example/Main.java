package org.example;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;
import java.time.ZoneId;

public class Main {
    void main() {

        Scanner scanner = new Scanner(System.in);


        String area;
        PriceData[] prices = null;
        PriceAnalyzer analyzer = new PriceAnalyzer();

        while (true) {

            IO.println();
            IO.println("Elpriser - Analysverktyg");
            IO.println("========================");
            IO.println("1. Välj elområde (SE1, SE2, SE3, SE4)");
            IO.println("2. Min, Max och Medelpris");
            IO.println("3. Sortera priser (lägst -> högst)");
            IO.println("4. Bästa laddningstid (4h sammanhängande)");
            IO.println("e. Avsluta");

            System.out.print("Välj ett alternativ: ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("e")) {
                IO.println("Programmet avslutas.");
                break;
            }

            switch (choice) {

                case "1":

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

                    try {
                        String data = apiClient.fetchData(url);

                        PriceDataParser parser = new PriceDataParser();
                        prices = parser.parse(data);

                        if(prices == null || prices.length == 0){
                            prices = null;
                            IO.println("Inga priser hittades.");
                            break;
                        }

                        IO.println("Priser hämtade för " + area);
                        IO.println("Antal priser: " + prices.length);

                    } catch (Exception e) {
                        prices = null;
                        IO.println("Kunde inte hämta priser: " + e.getMessage());
                    }
                    break;

                case "2":
                    if (prices == null) {
                        IO.println( "Välj först ett elområde genom alternativ 1.");
                        break;
                    }

                    double minPrice = analyzer.minPrice(prices);
                    double maxPrice = analyzer.maxPrice(prices);
                    double averagePrice = analyzer.averagePrice(prices);

                    IO.println("Lägsta pris: " + formatOre(minPrice));
                    IO.println("Högsta pris: " + formatOre(maxPrice));
                    IO.println("Medelpris: " + formatOre(averagePrice));

                    break;

                case "3":
                    if (prices == null) {
                        IO.println("Välj först ett elområde genom alternativ 1.");
                        break;
                    }

                    PriceData[] sortedPrices = analyzer.sortPrices(prices);

                    IO.println("Priser från billigast till dyrast: ");

                    for (PriceData price : sortedPrices) {
                        IO.println(formatOre(price.SEK_per_kWh()));
                    }
                    break;



                case "4":
                    if (prices == null) {
                        IO.println("Välj först ett elområde genom alternativ 1");
                        break;
                    }

                    PriceData[] bestChargingTime;
                    try {
                        bestChargingTime = analyzer.bestChargingTime(prices);
                    } catch (IllegalArgumentException e) {
                        IO.println(e.getMessage());
                        break;
                    }


                    String startTime = bestChargingTime[0].time_start().substring(11,16);
                    String endTime = bestChargingTime[bestChargingTime.length - 1].time_end().substring(11,16);

                    double chargingSum = 0;

                    for (PriceData price : bestChargingTime) {
                        chargingSum += price.SEK_per_kWh();
                    }

                    double chargingAverage = chargingSum / bestChargingTime.length;

                    IO.println("Bästa laddningstiden (4 timmar) är mellan: ");
                    IO.println( startTime + " - " + endTime);
                    IO.println("Medelpriset just då: " + formatOre(chargingAverage));

                    break;

                default:
                    IO.println("Ogiltigt val. Välj 1, 2, 3, 4 eller e.");
            }
        }

    }
    private static String formatOre(double sekPerkWh){
        return String.format(Locale.of("sv", "SE"), "%.0f", sekPerkWh * 100) + " öre/kWh";
    }
}
