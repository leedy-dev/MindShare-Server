package com.mindshare.cms.domain.menu.repository;

import com.mindshare.cmm.common.enums.MenuTypes;
import com.mindshare.cms.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByMenuType(MenuTypes menuType);

}
