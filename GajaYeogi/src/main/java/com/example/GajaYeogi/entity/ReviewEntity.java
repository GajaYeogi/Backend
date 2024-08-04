package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewid;                            // 게시글 ID

    @Column(name = "reviewtitle")
    private String reviewtitle;                       // 게시글 제목

    @Column(name = "reviewcontent")
    private String reviewcontent;                     // 게시글 본문

    @Column(name = "reviewuser")
    private String reviewuser;                        // 게시글 작성자 이메일

    @Column(name = "reviewcategory")                  // 게시글 카테고리
    private String reviewcategory;

    @Column(name = "reviewusername")
    private String reviewusername;                    // 게시글 작성자 닉네임

    @Column(name = "reviewtime")                      // 게시글 작성 시간
    private String reviewtime;

    @Column(name = "reviewlocation")
    private String reviewlocation;                    // 게시글의 지역

    @Column(name = "visitcount")                           //방문자 수
    private Long visitcount;

    @Column(name = "xpoint")
    private String reviewxpoint;                      // x좌표

    @Column(name = "ypoint")
    private String reviewypoint;                      // y좌표

    @OneToMany(mappedBy = "reviewentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImgEntity> reviewimage;
}
