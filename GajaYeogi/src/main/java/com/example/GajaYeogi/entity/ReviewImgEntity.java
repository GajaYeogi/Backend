package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReviewImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //게시글 이미지 아이디
    private Long reviewimageid;

    @Column(name = "reviewimgpath")                           //게시글 이미지 경로
    private String reviewimgpath;

    @ManyToOne
    @JoinColumn(name = "reviewid")                            //Join하기 위한 부모
    private ReviewEntity reviewentity;
}
