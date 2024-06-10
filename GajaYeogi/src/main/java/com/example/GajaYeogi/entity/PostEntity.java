package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postid;                            //게시글 id

    @Column(name = "posttitle")
    private String posttitle;                       //게시글 제목

    @Column(name = "postcontent")
    private String postcontent;                     //게시글 본문

    @Column(name = "postuser")
    private String postuser;                        //게시글 작성자 이메일

    @Column(name = "postusername")
    private String postusername;                    //게시글 작성자 닉네임

    @Column(name = "location")
    private String location;                        //작성하고 싶은 게시글의 지역

    @Column(name = "xpoint")
    private String xpoint;                          //x좌표

    @Column(name = "ypoint")
    private String ypoint;                          //y좌표

    @OneToMany(mappedBy = "auctionentity", cascade = CascadeType.ALL, orphanRemoval = true) //경매 이미지를 담기 위하여 AuctionImage 테이블과 연동
    private List<PostImgEntity> postimage;
}
