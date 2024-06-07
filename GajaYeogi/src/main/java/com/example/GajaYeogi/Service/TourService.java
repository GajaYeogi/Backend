package com.example.GajaYeogi.Service;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.boot.web.client.RestTemplateBuilder;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;
    import org.springframework.web.util.UriComponentsBuilder;

    @Service
    public class TourService {
        @Value("${tourapi.key}")
        private String apiKey;

        private final RestTemplate restTemplate;

        public TourService(RestTemplateBuilder restTemplateBuilder) {
            this.restTemplate = restTemplateBuilder.build();
        }

        public String getTourInfo() {
            String url = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/B551011/KorService1/locationBasedList1?")
                    .queryParam("serviceKey", apiKey)
                    .queryParam("pageNo", 1)
                    .queryParam("numOfRows", 10)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "AppTest")
                    .queryParam("_type", "json")
                    .queryParam("mapX", 126.981611)
                    .queryParam("mapY", 37.568477)
                    .queryParam("radius", 1000)
                    .queryParam("contentTypeId", 25)
                    .build(false) // 디코딩하지 않도록 설정
                    .toUriString();

            System.out.println(url);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        }
    }


