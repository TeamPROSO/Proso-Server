package com.prosoteam.proso.domain.user.entity;

import com.prosoteam.proso.global.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USER_TB")
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userIdx_PK")
    private Long id;

    @Column(nullable = false)
    private Long socialId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String profileImgUrl;

    @Column(nullable = false)
    private String socialType;

    @Column(columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    private String status;


    @Builder
    public User(String userName, String profileImgUrl, Long socialId, String socialType) {
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.socialId = socialId;
        this.socialType = socialType;
        this.status="ACTIVE";
    }

    public static User KakaoUser(Long socialId,String userName,String profileImgUrl){
        return User.builder()
                .socialId(socialId)
                .userName(userName)
                .profileImgUrl(profileImgUrl)
                .socialType("KAKAO")
                .build();

    }
    public User update(String userName) {
        this.userName = userName;

        return this;
    }

    // .. getter, setter 생략
}