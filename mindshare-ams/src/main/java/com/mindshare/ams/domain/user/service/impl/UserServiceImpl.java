package com.mindshare.ams.domain.user.service.impl;

import com.mindshare.ams.domain.user.repository.AmsUserQuerydslRepository;
import com.mindshare.ams.domain.user.service.UserService;
import com.mindshare.ams.domain.user.service.dto.UserRequestDto;
import com.mindshare.cmm.domain.user.entity.User;
import com.mindshare.cmm.domain.user.entity.UserInfo;
import com.mindshare.cmm.domain.user.repository.UserRepository;
import com.mindshare.cmm.domain.user.service.dto.UserDto;
import com.mindshare.core.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AmsUserQuerydslRepository amsUserQuerydslRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private User getUserEntityById(Long id) {
        return amsUserQuerydslRepository.findById(id)
                .orElseThrow(() -> new ApiException("User Not Found"));
    }

    @Override
    public UserDto.Response getUserById(Long id) {
        return modelMapper.map(getUserEntityById(id), UserDto.Response.class);
    }

    @Override
    @Transactional
    public UserDto.Response createUser(UserRequestDto.Join userRequestDto) {
        // set User
        User user = User.builder()
                .userType(userRequestDto.getUserType())
                .uid(userRequestDto.getUid())
                .password(userRequestDto.getPassword())
                .build();

        // set UserInfo
        UserInfo userInfo = UserInfo.builder()
                .name(userRequestDto.getUserInfo().getName())
                .email(userRequestDto.getUserInfo().getEmail())
                .build();

        // apply relation
        userInfo.applyUser(user);

        // save
        user = userRepository.save(user);

        // response
        return modelMapper.map(user, UserDto.Response.class);
    }

    @Override
    @Transactional
    public UserDto.Response updateUser(Long id, UserRequestDto.Update userRequestDto) {
        // get
        User user = getUserEntityById(id);

        // set
        modelMapper.map(userRequestDto, user);

        // response
        return modelMapper.map(user, UserDto.Response.class);
    }

    @Override
    @Transactional
    public Long deleteUserById(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
