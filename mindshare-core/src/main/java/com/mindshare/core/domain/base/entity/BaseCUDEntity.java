package com.mindshare.core.domain.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@SQLRestriction("delete_date_time is null")
public class BaseCUDEntity extends BaseCUEntity {

    @Column
    private LocalDateTime deleteDateTime;

    @Column
    private String deleteUserId;

    public void softDelete(String deleteUserId) {
        this.deleteDateTime = LocalDateTime.now();
        this.deleteUserId = deleteUserId;
    }

}
