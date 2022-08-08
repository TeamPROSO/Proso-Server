package com.prosoteam.proso.domain.content.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prosoteam.proso.domain.main.contentImg.model.ContentImg;
import com.prosoteam.proso.domain.theme.model.Theme;
import javax.persistence.*;

import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    public Theme getTheme(){
        return theme;
    }

    public void setTheme(Theme theme){
        this.theme = theme;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "content",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ContentImg> contentImgs;

}
