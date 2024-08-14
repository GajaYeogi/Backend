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

    @Column(name = "originalpostid")                          // 원글 ID
    private String originalpostid;

    @Column(name = "reviewcontent")
    private String reviewcontent;                     // 게시글 본문

    @Column(name = "reviewuser")
    private String reviewuser;                        // 게시글 작성자 이메일

    @Column(name = "reviewusername")
    private String reviewusername;                    // 게시글 작성자 닉네임

    @Column(name = "reviewtime")                      // 게시글 작성 시간
    private String reviewtime;

    @Column(name = "visitcount")                      //방문자 수
    private Long visitcount;

    @OneToMany(mappedBy = "reviewentity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImgEntity> reviewimage;
}
