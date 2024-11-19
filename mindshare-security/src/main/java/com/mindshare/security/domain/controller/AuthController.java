package com.mindshare.security.domain.controller;

import com.mindshare.security.common.component.CookieComponent;
import com.mindshare.security.domain.service.AuthService;
import com.mindshare.security.domain.service.dto.AuthDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieComponent cookieComponent;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Validated @RequestBody AuthDto.Login authDto,
            HttpServletResponse response,
            BindingResult bindingResult) {
        // login
        AuthDto.Token token = authService.login(authDto);

        // set cookie
        cookieComponent.setCookieRefreshToken(token.getRefreshToken(), response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
