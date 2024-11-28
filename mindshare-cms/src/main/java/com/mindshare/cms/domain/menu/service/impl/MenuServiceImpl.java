package com.mindshare.cms.domain.menu.service.impl;

import com.mindshare.cmm.common.components.LoginUserComponent;
import com.mindshare.cmm.common.enums.MenuTypes;
import com.mindshare.cmm.common.exception.ApiException;
import com.mindshare.cms.common.exception.CmsErrorMessage;
import com.mindshare.cms.domain.menu.entity.Menu;
import com.mindshare.cms.domain.menu.repository.MenuRepository;
import com.mindshare.cms.domain.menu.service.MenuService;
import com.mindshare.cms.domain.menu.service.dto.MenuDto;
import com.mindshare.core.common.utils.CommonObjectUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    private final LoginUserComponent loginUserComponent;

    private Menu getMenuEntityById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ApiException(CmsErrorMessage.MENU_NOT_FOUND));
    }

    @Override
    public MenuDto.Response getMenuById(Long id) {
        Menu menu = getMenuEntityById(id);

        return modelMapper.map(menu, MenuDto.Response.class);
    }

    @Override
    public List<MenuDto.Response> getMenuList(MenuTypes menuType) {
        return menuRepository.findAllByMenuType(menuType).stream()
                .map(menu -> modelMapper.map(menu, MenuDto.Response.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MenuDto.Response createMenu(MenuDto.Request menuDto) {
        // set menu
        Menu menu = Menu.builder()
                .menuType(menuDto.getMenuType())
                .name(menuDto.getName())
                .description(menuDto.getDescription())
                .build();

        // apply parent
        if (CommonObjectUtils.nonNull(menuDto.getParentId())) {
            Menu parent = getMenuEntityById(menuDto.getParentId());

            parent.addChild(menu);
        }

        // save
        menu = menuRepository.save(menu);

        // response
        return modelMapper.map(menu, MenuDto.Response.class);
    }

    @Override
    @Transactional
    public MenuDto.Response updateMenu(Long id, MenuDto.Request menuDto) {
        // get
        Menu menu = getMenuEntityById(id);

        // update
        modelMapper.map(menuDto, menu);

        if (CommonObjectUtils.nonNull(menuDto.getParentId())) {
            Menu parent = getMenuEntityById(menuDto.getParentId());
            menu.applyParent(parent);
        }

        return modelMapper.map(menu, MenuDto.Response.class);
    }

    @Override
    @Transactional
    public Long deleteMenu(Long id) {
        // get
        Menu menu = getMenuEntityById(id);

        // soft delete
        menu.softDelete(loginUserComponent.getCurrentLoginUser());

        return id;
    }
}
