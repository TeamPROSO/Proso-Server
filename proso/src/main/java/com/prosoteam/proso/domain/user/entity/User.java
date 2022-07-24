package com.prosoteam.proso.domain.user.entity;

import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "USER_TB")
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userIdx_PK")
    private Long id;


    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String profileImgUrl;
    @Column(nullable = false)
    private Long socialId;
    @Column(nullable = false)
    private String socialType;
    //private String email;

    @Column(columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    private String status;

    public User(String userName, String profileImgUrl, Long socialId, String socialType) {
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.socialId = socialId;
        this.socialType = socialType;
        this.status="ACTIVE";
    }

    public User update(String userName) {
        this.userName = userName;

        return this;
    }

    // .. getter, setter 생략
}