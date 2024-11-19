package com.mindshare.security.domain.service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthDto {

    @Getter
    public static class Login {
        @NotEmpty
        private String uid;

        @NotEmpty
        private String password;
    }

    @Getter
    @Builder
    public static class Token {
        private String accessToken;
        private String refreshToken;
    }

}
