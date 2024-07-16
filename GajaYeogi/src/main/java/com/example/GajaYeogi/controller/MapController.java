package com.example.GajaYeogi.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
//좌표를 받을시에 주소를 반환
public class MapController {
    private final String NAVER_API_URL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc";
    private final String CLIENT_ID = "brefyohb9j";
    private final String CLIENT_SECRET = "Z6Uns6TD91Oyi5YQk4vJK1On0rGlykUUlULJVVuR";

    @PostMapping("/get-location")
    public ResponseEntity<Map<String, Object>> getLocation(@RequestBody Map<String, String> coords) {
        String latitude = coords.get("latitude");
        String longitude = coords.get("longitude");

        RestTemplate restTemplate = new RestTemplate();
        String url = NAVER_API_URL + "?coords=" + longitude + "," + latitude + "&output=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
        headers.set("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        Map<String, Object> result = new HashMap<>();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            result.put("location", response.getBody());
        } else {
            result.put("location", "Error fetching location data");
        }

        return ResponseEntity.ok(result);
    }
}
