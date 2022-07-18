package com.prosoteam.proso.global.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.Algorithm;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class SocialController {
    private final SocialService socialService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;
    private SocialDto socialDto;

    @PostMapping("/users/login/{provider}")
    public ResponseEntity socialLogin(@PathVariable String provider,
                                      @RequestBody(required = false) UserDto userDto,
                                      @RequestParam String code,
                                      HttpServletResponse response) throws JsonProcessingException {

        if (provider.equals("kakao")) {
            socialDto = socialService.verificationKakao(code);
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<UserEntity> users = userRepository.findByEmail(socialDto.getEmail());

        // 서비스에 등록된 회원이 아니라면
        if (users.isEmpty()) {
            userDto.setEmail(socialDto.getEmail());
            userDto.setName(socialDto.getName());
            userDto.setImageUrl(socialDto.getImageUrl());
            userDto.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
            UserEntity userEntity = DtoToEntity.userDtoToEntity(userDto);
            // 회원가입
            userRepository.save(userEntity);
        }

        // JWT 토큰 생성
        String token = JWT.create()
                .withSubject("JwtToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .withClaim("email", socialDto.getEmail())
                .sign(Algorithm.HMAC512(env.getProperty("token.secret")));

        // JWT 토큰 헤더에 담아 전달
        response.addHeader(env.getProperty("token.header"), env.getProperty("token.prefix") + token);

        return new ResponseEntity(HttpStatus.OK);
    }
}
