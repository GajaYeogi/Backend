package com.example.GajaYeogi.controller;

import com.example.GajaYeogi.dto.PostDto;
import com.example.GajaYeogi.service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    //투어 api 사용
    @GetMapping("/info")
    public ResponseEntity<String> getTourInfo(@RequestParam(value = "postxpoint") String postxpoint,
                                              @RequestParam(value = "postypoint") String postypoint){

        PostDto postDto = new PostDto();
        postDto.setPostxpoint(postxpoint);
        postDto.setPostypoint(postypoint);

        logger.info("Received request for tour info");
        String tourInfo = tourService.getTourInfo(postDto);
        if ("Error fetching tour info".equals(tourInfo)) {
            return ResponseEntity.status(500).body(tourInfo);
        }
        return ResponseEntity.ok(tourInfo);
    }
}
