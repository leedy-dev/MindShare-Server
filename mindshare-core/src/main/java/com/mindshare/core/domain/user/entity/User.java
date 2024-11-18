package com.mindshare.core.domain.user.entity;

import com.mindshare.core.common.converters.BCryptoConverter;
import com.mindshare.core.domain.base.entity.BaseCUDEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@SuperBuilder
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"uid"})
        }
)
@DiscriminatorColumn(name = "DTYPE")
public class User extends BaseCUDEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = 0L;

    @Column(insertable = false, updatable = false)
    private String dtype;

    @NotNull
    @Size(min = 5, max = 30)
    @Column
    private String uid;

    @NotNull
    @Size(max = 200)
    @Convert(converter = BCryptoConverter.class)
    @Column
    private String password;

    @Column(columnDefinition = "integer default 0")
    private Integer loginAttemptCount = 0;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime lastLoginDateTime;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    /* user details */
    @Builder.Default
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean accountExpired = false;

    @Builder.Default
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean credentialExpired = false;

    @Builder.Default
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean locked = false;

    @Builder.Default
    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return uid;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void applyUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
