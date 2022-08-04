package com.prosoteam.proso.domain.content.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.user.entity.User;

import javax.persistence.*;

import javax.persistence.*;

import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CONTENT_TB")

public class Content extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contentIdx_PK")
    private Long id;

    @Column(nullable = false)
    private String contentTitle;

    @Column(nullable = false)
    private String contentContent;


    @ManyToOne
    @JoinColumn(name = "themeIdx_FK")
    @JsonManagedReference
    private Theme theme;


    /*
    @ManyToOne
    @JoinColumn(name = "userIdx_FK")
    @JsonManagedReference
    private User user;
    */

}
