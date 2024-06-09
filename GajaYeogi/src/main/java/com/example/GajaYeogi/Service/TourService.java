package com.example.GajaYeogi.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class TourService {

    @Value("${tourapi.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public TourService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getTourInfo() {
        String urlRoot = "http://apis.data.go.kr/B551011/KorService1/locationBasedList1";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(urlRoot)
                .queryParam("serviceKey", apiKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("_type", "json")
                .queryParam("mapX", 126.981611)
                .queryParam("mapY", 37.568477)
                .queryParam("radius", 1000)
                .queryParam("contentTypeId", 25);

        URI uri;
        try {
            uri = new URI(uriBuilder.toUriString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating URI", e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "*/*;q=0.9");

        HttpEntity<String> httpRequest = new HttpEntity<>(null, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, httpRequest, new ParameterizedTypeReference<String>() {});
        } catch (RestClientException e) {
            throw new RuntimeException("Error fetching tour info", e);
        }

        return response.getBody();
    }
}
