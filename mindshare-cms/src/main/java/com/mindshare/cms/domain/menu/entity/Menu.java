package com.mindshare.cms.domain.menu.entity;

import com.mindshare.cmm.common.enums.MenuTypes;
import com.mindshare.core.domain.base.entity.BaseCUDEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = { "parent", "children" })
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(
        name = "menu"
)
public class Menu extends BaseCUDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = 0L;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length = 10)
    private MenuTypes menuType;

    @NotNull
    @Size(max = 30)
    @Column
    private String name;

    @NotNull
    @Size(max = 30)
    @Column
    private String description;

    @Builder.Default
    @NotNull
    @Column(columnDefinition = "integer default 1")
    private Integer index = 1;

    @Builder.Default
    @NotNull
    @Column
    private Integer level = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_parent_menu_id"))
    private Menu parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> children = new ArrayList<>();

    public void addChild(Menu child) {
        child.applyParent(this);

        this.children.add(child);
    }

    public void applyParent(Menu parent) {
        this.parent = parent;
        this.level = parent.level + 1;
    }

}
