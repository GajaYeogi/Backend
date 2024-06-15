package com.example.GajaYeogi.controller;

import com.example.GajaYeogi.dto.PostDto;
import com.example.GajaYeogi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
    
    //글 작성
    @GetMapping("/postwirte")
    public ResponseEntity<String> writepost(@ModelAttribute PostDto postDto){
        try{
            postService.postwrite(postDto);
            return ResponseEntity.ok("글 작성 완료!");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("505 예기치 못한 오류입니다");
        }
    }

    //모든 글 조회
    @GetMapping("/postallread")
    public ResponseEntity<List<PostDto>> postallread(){
        try{
            List<PostDto> postlist = postService.getAllPost();
            return ResponseEntity.ok(postlist);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //특정 글 조회
    @GetMapping("/postread")
    public ResponseEntity<PostDto> postread(@RequestParam(value = "postid") String postid){
        try{
            PostDto postDto = new PostDto();
            postDto.setPostid(Long.valueOf(postid));

            PostDto postlist = postService.getPost(postDto);
            return ResponseEntity.ok(postlist);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
