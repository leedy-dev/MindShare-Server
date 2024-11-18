package com.mindshare.core.domain.base.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class IdDto {

    @NotNull
    private Long id;

}
