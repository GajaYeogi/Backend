package com.example.GajaYeogi.Controller;

import com.example.GajaYeogi.Service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tour")
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/info")
    public ResponseEntity<String> getTourInfo() {
        String tourInfo = tourService.getTourInfo();
        return ResponseEntity.ok(tourInfo);
    }
}
