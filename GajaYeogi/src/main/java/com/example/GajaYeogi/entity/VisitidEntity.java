package com.example.GajaYeogi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "visitid")
@Data
public class VisitidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)       //작성글 구별 ID
    private Long idvisitid;

    @Column(name = "writeid")                                 //방문한 장소 ID
    private String visitid;

    @ManyToOne
    @JoinColumn(name = "userid")                              //Join하기 위한 부모
    private UserEntity userentity;
}
