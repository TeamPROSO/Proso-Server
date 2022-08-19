package com.prosoteam.proso.domain.contentImg.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CONTENT_IMG_TB")

public class ContentImg extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contentImgUrlIdx_PK")
    private Long id;


    @Column(nullable = false, length=5000)
    private String contentImgUrl;

    @Column(columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    private String status;


    @ManyToOne
    @JoinColumn(name = "contentIdx_FK")
    @JsonManagedReference
    private Content content;

}
