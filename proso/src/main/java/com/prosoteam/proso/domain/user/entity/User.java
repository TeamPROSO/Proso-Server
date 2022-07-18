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
    private Long id;

    private String picture;
    private String nickname;

    @Column(nullable = false)
    private String email;
    private String name;


    @Column(nullable = false)
    private String role = "ROLE_USER";

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User update(String name) {
        this.name = name;

        return this;
    }

    // .. getter, setter 생략
}