package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "usertable")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)         //유저 식별 번호
    private Long userid;

    @Column(name = "user")                                      //유저 이메일
    private String user;

    @Column(name = "username")                                  //유저 닉네임
    private String username;

    @Column(name = "Introduction")
    private String Introduction;

    @OneToMany(mappedBy = "userentity", cascade = CascadeType.ALL, orphanRemoval = true)  //작성한 게시글 ID
    private List<PostWriteidEntity> postwriteid;

    @OneToMany(mappedBy = "userentity", cascade = CascadeType.ALL, orphanRemoval = true)  //작성한 게시글 ID
    private List<ReviewWriteidEntity> reviewwriteid;

    @OneToMany(mappedBy = "userentity", cascade = CascadeType.ALL, orphanRemoval = true) //스크랩한 게시글 ID
    private List<ScrapEntity> scraps;

    @OneToMany(mappedBy = "userentity", cascade = CascadeType.ALL, orphanRemoval = true) //방문자 게시글 ID
    private List<VisitidEntity> visitid;
}