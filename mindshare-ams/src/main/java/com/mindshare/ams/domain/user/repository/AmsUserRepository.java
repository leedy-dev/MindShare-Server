package com.mindshare.ams.domain.user.repository;

import com.mindshare.core.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmsUserRepository extends JpaRepository<User, Long> {
}
