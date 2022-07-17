package com.prosoteam.proso.global.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.Collections;

@Configuration
public class OAuthConfiguration {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            @Value("{kakao.client-id}") String clientId,
            @Value("${kakao.client-secret}")String clientSecret) {
        final ClientRegistration clientRegistration = CustomOAuthProvider.KAKAO
                .getBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        return new InMemoryClientRegistrationRepository(Collections.singletonList(
                clientRegistration
        ));
    }
}
