package org.example;

public class Main {
    void main() throws Exception {
        ApiClient apiClient = new ApiClient();
        String url = "https://www.elprisetjustnu.se/api/v1/prices/2026/08-24_SE3.json";
        String data = apiClient.fetchData(url);

        IO.println(data);
    }
}
