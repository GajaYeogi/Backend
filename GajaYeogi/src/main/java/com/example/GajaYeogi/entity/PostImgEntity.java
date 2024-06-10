package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PostImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //게시글 이미지 아이디
    private Long postimageid;

    @Column(name = "postimgpath")                      //게시글 이미지 경로
    private String postimgpath;

    @ManyToOne
    @JoinColumn(name = "postid")                         //Join하기 위한 부모
    private PostEntity postentity;
}
