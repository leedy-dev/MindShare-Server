package com.mindshare.core.common.audit;

import com.mindshare.core.common.utils.CommonObjectUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class LoginUserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (CommonObjectUtils.isNull(authentication) || !authentication.isAuthenticated()) {
            return Optional.empty();
        } else {
            return Optional.of(authentication.getName());
        }
    }

}
