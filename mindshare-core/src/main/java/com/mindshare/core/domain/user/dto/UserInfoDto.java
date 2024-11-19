package com.mindshare.core.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserInfoDto {

    @NotEmpty
    @Size(max = 30)
    private String name;

    @NotEmpty
    @Size(max = 200)
    private String email;

}
