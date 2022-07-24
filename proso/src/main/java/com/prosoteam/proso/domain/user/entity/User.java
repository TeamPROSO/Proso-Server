package com.prosoteam.proso.domain.user.entity;

import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "USER_TB")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userIdx_PK")
    private Long id;


    @Column(nullable = false)
    private String userName;
    private String profileImgUrl;
    private Long socialId;
    private String socialType;
    //private String email;

    private String status;

    public User(String userName, String profileImgUrl,Long socialId,String socialType) {
        this.userName =userName;
        this.profileImgUrl = profileImgUrl;
        this.socialId=socialId;
        this.socialType=socialType;
    }

    public User update(String userName) {
        this.userName = userName;

        return this;
    }

    // .. getter, setter 생략
}