package com.mindshare.cmm.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@SuperBuilder
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "user")
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(
        name = "user_info"
)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = 0L;

    @NotNull
    @Size(max = 30)
    @Column
    private String name;

    @NotNull
    @Size(max = 200)
    @Column
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_info"))
    private User user;

    public void applyUser(User user) {
        this.user = user;

        this.user.applyUserInfo(this);
    }

}
