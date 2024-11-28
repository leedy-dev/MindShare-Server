package com.mindshare.cms.domain.menu.service.dto;

import com.mindshare.cmm.common.enums.MenuTypes;
import com.mindshare.core.domain.base.dto.BaseCUDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuDto extends BaseCUDto {

    @NotNull
    private MenuTypes menuType;

    @NotEmpty
    @Size(max = 30)
    private String name;

    @NotEmpty
    @Size(max = 30)
    private String description;

    private Integer index;

    private Integer level;

    @Getter
    public static class Request extends MenuDto {

        private Long parentId;

    }

    @Getter
    public static class Response extends MenuDto {

        private Long id;

        private List<Response> children;

    }

}
