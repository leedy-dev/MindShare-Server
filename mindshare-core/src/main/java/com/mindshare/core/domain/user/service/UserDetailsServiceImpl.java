package com.mindshare.core.domain.user.service;

import com.mindshare.core.domain.user.entity.User;
import com.mindshare.core.domain.user.repository.UserRepository;
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

        return userOp.orElseThrow(() -> new UsernameNotFoundException(uid));
    }

}
