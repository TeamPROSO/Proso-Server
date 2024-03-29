package com.prosoteam.proso.global.common;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum ErrorCode {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, "1000", "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, "2000", "입력값을 확인해주세요."),
    EMPTY_JWT(false, "2001", "JWT를 입력해주세요."),
    EXPIRED_JWT(false, "2002", "만료된 ACCESS TOKEN 입니다."),
    INVALID_USER_JWT(false,"2003","권한이 없는 유저의 접근입니다."),
    METHOD_NOT_ALLOWED(false,"2004","지원하지 않는 HTTP method 입니다."),
    UNSUPPORTED_MEDIA_TYPE(false,"2005","지원하지 않는 미디어 타입입니다."),
    INVALID_ACCESS_TOKEN(false,"2006","잘못된 ACCESS TOKEN 입니다."),
    INVALID_REFRESH_TOKEN(false,"2007","잘못된 REFRESH TOKEN 입니다."),
    TAMPERED_REFRESH_TOKEN(false,"2008","DB에 저장된 값과 들어온 REFRESH TOKEN 값이 다릅니다."),
    // users
    USERS_EMPTY_USER_ID(false, "2010", "유저 아이디 값을 확인해주세요."),

    //main
    MAIN_RESPONSE_EMPTY(false, "2014","카카오맵 API에서 응답 받지 못했습니다."),

    THEME_USERS_EMPTY(false,"2015","작성한 테마가 없습니다."),

    BOOKMARK_USERS_EMPTY(false, "2016", "북마크한 테마가 없습니다."),
    THEME_SEARCH_RESULT_EMPTY(false,"2017","검색된 테마가 없습니다."),

    POST_POSTS_INVALID_CONTENTS(false,"2018","내용의 글자수를 확인해주세요."),
    POST_POSTS_EMPTY_IMGURL(false,"2019","게시물의 이미지를 입력해주세요."),

    POSTS_EMPTY_POST_ID(false, "2020", "게시물 아이디 값을 확인해주세요."),

    POST_USERS_EMPTY_PASSWORD(false, "2030", "비밀번호를 입력해주세요."),
    CONTENT_USERS_EMPTY(false,"2021","작성한 컨텐츠가 없습니다."),
    CONTENT_IMG_USERS_EMPTY(false,"2022","작성한 컨텐츠 이미지가 없습니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, "3000", "값을 불러오는데 실패하였습니다."),

    // [POST] /users

    MODIFY_FAIL_POST(false,"3020","게시물 수정을 실패했습니다."),
    DELETE_FAIL_POST(false,"3021","게시물 삭제를 실패했습니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, "4000", "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, "4001", "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,"4014","유저네임 수정 실패");



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final String code;
    private final String message;


    private ErrorCode(boolean isSuccess, String code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
