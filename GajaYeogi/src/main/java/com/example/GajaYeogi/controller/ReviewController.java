package com.example.GajaYeogi.controller;

import com.example.GajaYeogi.dto.ReviewDto;
import com.example.GajaYeogi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    //글 작성 reviewuser, reviewusername, reviewtitle, reviewcontent, reviewimg, reviewlocation, xpoint, ypoint
    @GetMapping("/reviewwirte")
    public ResponseEntity<String> writereview(@ModelAttribute ReviewDto reviewDto){
        try{
            String reviewresponse = reviewService.Reviewwrite(reviewDto);
            return ResponseEntity.ok(reviewresponse);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("505 예기치 못한 오류입니다");
        }
    }

    //모든 글 조회
    @GetMapping("/reviewallread")
    public ResponseEntity<List<ReviewDto>> reviewallread(){
        try{
            List<ReviewDto> reviewlist = reviewService.getAllReview();
            return ResponseEntity.ok(reviewlist);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //특정 글 조회
    @GetMapping("/reviewread")
    public ResponseEntity<ReviewDto> reviewread(@RequestParam(value = "reviewid") String reviewid){
        try{
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewid(reviewid);

            ReviewDto reviewlist = reviewService.getReview(reviewDto);
            return ResponseEntity.ok(reviewlist);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //게시글 수정 reviewid, reviewimg, reviewoldimg, reviewtitle, reviewcontent, reviewlocation, xpoint, ypoint
    @PutMapping("/reviewupdate")
    public ResponseEntity<String> reviewupdate(@ModelAttribute ReviewDto reviewDto){
        try{
            String reviewresponse = reviewService.updateReview(reviewDto);
            return ResponseEntity.ok(reviewresponse);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("505 예기치 못한 오류입니다");
        }
    }

    //게시글 삭제
    @DeleteMapping("/reviewdelete")
    public ResponseEntity<String> reviewdelete(@RequestParam(value = "reviewid")String reviewid,
                                             @RequestParam(value = "reviewuser")String reviewuser){
        try{
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewid(reviewid);
            reviewDto.setReviewuser(reviewuser);

            String reviewresponse = reviewService.deleteReview(reviewDto);
            return ResponseEntity.ok(reviewresponse);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("505 예기치 못한 오류입니다");
        }
    }
}