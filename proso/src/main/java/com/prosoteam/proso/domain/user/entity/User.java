package com.prosoteam.proso.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_TB")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userIdx_PK")
    private Long id;

    @Column(nullable = false, unique = true)
    private Long socialId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String profileImgUrl;

    @Column(nullable = false)
    private String socialType;

    @Column(columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    private String status;

    private String refreshToken;

    private String role;

    @Builder
    public User(String userName, String profileImgUrl, Long socialId, String socialType, String status,String role) {
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.socialId = socialId;
        this.socialType = socialType;
        this.status=status;
        this.role=role;
    }

    public static User KakaoUser(Long socialId,String userName,String profileImgUrl){
        return User.builder()
                .socialId(socialId)
                .userName(userName)
                .profileImgUrl(profileImgUrl)
                .socialType("KAKAO")
                .build();

    }
    public void updateUserName(String userName) {
        this.userName = userName;
    }
    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
    public void updateBoth(String userName,String profileImgUrl) {
        this.userName = userName;
        this.profileImgUrl=profileImgUrl;
    }


    public User updateRefreshToken(String refreshToken){
        this.refreshToken=refreshToken;
        return this;
    }

    public void updateRole(String role){
        this.role=role;
    }
    // .. getter, setter 생략



}