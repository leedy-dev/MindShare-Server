package com.mindshare.ams.domain.user.service;

import com.mindshare.ams.domain.user.service.dto.UserRequestDto;
import com.mindshare.cmm.domain.user.service.dto.UserDto;

public interface UserService {

    UserDto.Response getUserById(Long id);

    UserDto.Response createUser(UserRequestDto.Join userRequestDto);

    UserDto.Response updateUser(Long id, UserRequestDto.Update userRequestDto);

    Long deleteUserById(Long id);

}
