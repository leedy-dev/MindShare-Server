package com.mindshare.ams.domain.user.repository;

import com.mindshare.cmm.domain.user.entity.QUser;
import com.mindshare.cmm.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AmsUserQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<User> findById(Long id) {
        QUser user = QUser.user;

        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .where(user.id.eq(id))
                .fetchOne());
    }

}
