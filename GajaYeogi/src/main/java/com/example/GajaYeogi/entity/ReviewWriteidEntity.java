package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "userreviewwriteid")
@Data
public class ReviewWriteidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)       //작성글 구별 ID
    private Long idreviewwriteid;

    @Column(name = "writeid")                                 //작성한 게시글 ID
    private String reviewwriteid;

    @ManyToOne
    @JoinColumn(name = "userid")                              //Join하기 위한 부모
    private UserEntity userentity;
}
