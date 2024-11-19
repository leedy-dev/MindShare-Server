package com.mindshare.security.domain.service;

import com.mindshare.security.domain.service.dto.AuthDto;

public interface AuthService {

    AuthDto.Token login(AuthDto.Login authDto);

}
