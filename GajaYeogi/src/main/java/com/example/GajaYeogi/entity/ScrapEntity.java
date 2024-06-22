package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "userscrap")
@Data
public class ScrapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //스크랩 구별 ID
    private Long idscrap;

    @Column(name = "scrapid")                                 //스크랩한 게시글 ID
    private String scrapid;

    @ManyToOne
    @JoinColumn(name = "userid")                              //Join하기 위한 부모
    private UserEntity userentity;
}
