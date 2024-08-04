package com.example.GajaYeogi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ReviewDto {
    private String reviewid;                            //게시글 id
    private String reviewtitle;                       //게시글 제목
    private String reviewcontent;                     //게시글 본문
    private String reviewuser;                        //게시글 작성자 이메일
    private String reviewusername;                    //게시글 작성자 닉네임
    private String reviewlocation;                    //작성하고 싶은 게시글의 지역
    private Long visitcount;
    private String reviewxpoint;                          //x좌표
    private String reviewypoint;                          //y좌표
    private List<MultipartFile> reviewimg;            //이미지
    private List<String> reviewoldimg;                //원래 있었던 이미지
    private List<String> reviewimgurl;                //이미지 경로를 반환하기 위한 변수
}
