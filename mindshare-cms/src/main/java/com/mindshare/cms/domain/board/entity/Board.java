package com.mindshare.cms.domain.board.entity;

import com.mindshare.core.domain.base.entity.BaseCUEntity;
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
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(
        name = "board"
)
public class Board extends BaseCUEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = 0L;

    @NotNull
    @Size(max = 100)
    @Column
    private String title;

    @NotNull
    @Size(max = 4000)
    @Column
    private String content;

    @Builder.Default
    @Column(columnDefinition = "integer default 0")
    private Integer viewCount = 0;

}
