package com.example.GajaYeogi.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long userid;            //유저 식별 번호
    private String user;            //유저 이메일
    private String username;        //유저 닉네임
    private String Introduction;    //자기소개
    private List<String> scrapids;    //스크랩한 게시글 ID
    private List<String> visitids;    //방문한 글 ID
    private List<String> postwriteids;    //작성한 post 게시글 ID
    private List<String> reviewwriteids;    //작성한 review 게시글 ID
}
