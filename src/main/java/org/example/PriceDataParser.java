package org.example;

import tools.jackson.databind.ObjectMapper;

public class PriceDataParser {

    public PriceData[] parse(String json) {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(json, PriceData[].class);
    }
}
