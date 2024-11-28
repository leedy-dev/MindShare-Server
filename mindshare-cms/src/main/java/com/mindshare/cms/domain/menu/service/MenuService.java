package com.mindshare.cms.domain.menu.service;

import com.mindshare.cms.domain.menu.service.dto.MenuDto;
import com.mindshare.cmm.common.enums.MenuTypes;

import java.util.List;

public interface MenuService {

    MenuDto.Response getMenuById(Long id);

    List<MenuDto.Response> getMenuList(MenuTypes menuType);

    MenuDto.Response createMenu(MenuDto.Request menuDto);

    MenuDto.Response updateMenu(Long id, MenuDto.Request menuDto);

    Long deleteMenu(Long id);

}
