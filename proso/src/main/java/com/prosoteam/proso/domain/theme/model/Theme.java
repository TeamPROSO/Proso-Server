package com.prosoteam.proso.domain.theme.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prosoteam.proso.domain.user.entity.User;

import javax.persistence.*;

import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "THEME_TB")

public class Theme extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "themeIdx_PK")
    private Long id;

    @Column(nullable = false)
    private String themeTitle;

    @Column(nullable = false)
    private String themeIntroduce;

    @Column(length = 5000)
    private String themeImgUrl;

    @ManyToOne
    @JoinColumn(name = "userIdx_FK")
    @JsonManagedReference
    private User user;

}
