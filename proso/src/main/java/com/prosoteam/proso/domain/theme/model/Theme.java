package com.prosoteam.proso.domain.theme.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.user.entity.User;

import javax.persistence.*;

import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
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

    @Column(columnDefinition = "varchar (25) default 'INACTIVE'")
    private String status;


    @ManyToOne
    @JoinColumn(name = "userIdx_FK")
    @JsonManagedReference
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "theme",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<Content> contents = new ArrayList<>();



    public int getCountOfContents(){
        return this.contents.size();
    }


    public List<Content> getContentsList(Long themeId){
        return this.contents;
    }

    public Long getThemeId(){
        return this.id;
    }


    public String changeStatus(){
        return this.status = "ACTIVE";
    }

    public String reChangeStatus(){
        return this.status = "INACTIVE";
    }


}

