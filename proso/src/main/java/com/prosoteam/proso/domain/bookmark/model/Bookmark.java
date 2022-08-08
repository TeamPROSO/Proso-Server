package com.prosoteam.proso.domain.bookmark.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "BOOKMARK_TB")
public class Bookmark extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmarkIdx_PK")
    private Long bookmarkIdx;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookmarkStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "themeId")
    @JsonManagedReference
    private Theme theme;

    public Bookmark(BookmarkStatus status,User user ,Theme theme){
        this.status =status;
        this.user=user;
        this.theme=theme;
    }

    public Bookmark updateStatus(BookmarkStatus status){
        this.status=status;
        return this;
    }

}
