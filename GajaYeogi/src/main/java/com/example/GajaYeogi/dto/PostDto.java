package com.example.GajaYeogi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostDto {
    private String postid;                            //게시글 id
    private String posttitle;                       //게시글 제목
    private String postcontent;                     //게시글 본문
    private String postuser;                        //게시글 작성자 이메일
    private String postusername;                    //게시글 작성자 닉네임
    private String postlocation;                        //작성하고 싶은 게시글의 지역
    private String postxpoint;                          //x좌표
    private String postypoint;                          //y좌표
    private List<MultipartFile> postimg;            //이미지
    private List<String> postoldimg;                //원래 있었던 이미지
    private List<String> postimgurl;                //이미지 경로를 반환하기 위한 변수
    private Long suggest;                           //추천
}