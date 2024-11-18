package com.mindshare.security.domain.service.impl;

import com.mindshare.core.domain.user.entity.User;
import com.mindshare.security.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String uid) throws UsernameNotFoundException {
        Optional<User> userOp = userRepository.findByUid(uid);

        // 실제 구현에서는 데이터베이스에서 사용자 정보를 가져옵니다.
        return userOp.orElseThrow(() -> new UsernameNotFoundException(uid));
    }

}
