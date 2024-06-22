package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postid;                            // 게시글 ID

    @Column(name = "posttitle")
    private String posttitle;                       // 게시글 제목

    @Column(name = "postcontent")
    private String postcontent;                     // 게시글 본문

    @Column(name = "postuser")
    private String postuser;                        // 게시글 작성자 이메일

    @Column(name = "postcategory")                  // 게시글 카테고리
    private String postcategory;

    @Column(name = "postusername")
    private String postusername;                    // 게시글 작성자 닉네임

    @Column(name = "posttime")                      // 게시글 작성 시간
    private String posttime;

    @Column(name = "location")
    private String location;                        // 게시글의 지역

    @Column(name = "xpoint")
    private String xpoint;                          // x좌표

    @Column(name = "ypoint")
    private String ypoint;                          // y좌표

    @Column(name = "suggest")
    private Long suggest;                           //추천

    @OneToMany(mappedBy = "postentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImgEntity> postimage;
}
