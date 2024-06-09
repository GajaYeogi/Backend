package com.example.GajaYeogi.Controller;

import com.example.GajaYeogi.Service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/tour")
public class TourController {

    private static final Logger logger = LoggerFactory.getLogger(TourController.class);

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/info")
    public ResponseEntity<String> getTourInfo() {
        logger.info("Received request for tour info");
        String tourInfo = tourService.getTourInfo();
        if ("Error fetching tour info".equals(tourInfo)) {
            return ResponseEntity.status(500).body(tourInfo);
        }
        return ResponseEntity.ok(tourInfo);
    }
}
