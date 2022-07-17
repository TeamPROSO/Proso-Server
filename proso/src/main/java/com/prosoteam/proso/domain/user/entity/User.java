package com.prosoteam.proso.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER_TB")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_IDX")
    private Long userIdx;

    @Column(nullable = false,name = "USERNAME")
    private String  username;

    @Column(nullable = false,unique = true,length =50,name= "NICKNAME")
    private String nickname;

    @Column(nullable = false, length = 100,name = "PASSWORD")
    private String password;

    @Column(name = "USER_Img")
    private String userImg ;
}
