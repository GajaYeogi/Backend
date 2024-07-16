package com.example.GajaYeogi.controller;

import com.example.GajaYeogi.dto.PostDto;
import com.example.GajaYeogi.entity.PostEntity;
import com.example.GajaYeogi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }
    
    //글 작성 postuser, postusername, posttitle, postcontent, postimg, location, xpoint, ypoint
    @GetMapping("/postwirte")
    public ResponseEntity<String> writepost(@ModelAttribute PostDto postDto){
        try{
            String postresponse = postService.postwrite(postDto);
            return ResponseEntity.ok(postresponse);
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
            postDto.setPostid(postid);

            PostDto postlist = postService.getPost(postDto);
            return ResponseEntity.ok(postlist);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //게시글 추천
    @GetMapping("/postsuggest")
    public ResponseEntity<String> postsuggest(@RequestParam(value = "postid") String postid,
                                              @RequestParam(value = "postuser") String postuser){
        try{
            PostDto postDto = new PostDto();
            postDto.setPostid(postid);
            postDto.setPostuser(postuser);

            String postresponse = postService.suggestPost(postDto);
            return ResponseEntity.ok(postresponse);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //게시글 추천 취소
    @GetMapping("/unpostsuggest")
    public ResponseEntity<String> unpostsuggest(@RequestParam(value = "postid") String postid,
                                                @RequestParam(value = "postuser") String postuser){
        try{
            PostDto postDto = new PostDto();
            postDto.setPostid(postid);
            postDto.setPostuser(postuser);

            String postresponse = postService.unsuggestPost(postDto);
            return ResponseEntity.ok(postresponse);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //게시글 수정 postid, postimg, postoldimg, posttitle, postcontent, location, xpoint, ypoint

    @PutMapping("/postupdate")
    public ResponseEntity<String> postupdate(@ModelAttribute PostDto postDto){
        try{
            String postresponse = postService.updatePost(postDto);
            return ResponseEntity.ok(postresponse);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("505 예기치 못한 오류입니다");
        }
    }

    //게시글 삭제
    @DeleteMapping("/postdelete")
    public ResponseEntity<String> postdelete(@RequestParam(value = "postid")String postid,
                                             @RequestParam(value = "postuser")String postuser){
        try{
            PostDto postDto = new PostDto();
            postDto.setPostid(postid);
            postDto.setPostuser(postuser);

            String postresponse = postService.deletePost(postDto);
            return ResponseEntity.ok(postresponse);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("505 예기치 못한 오류입니다");
        }
    }
}
