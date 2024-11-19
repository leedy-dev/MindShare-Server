package com.mindshare.ams.domain.user.service.dto;

import com.mindshare.core.common.enums.UserTypes;
import com.mindshare.core.domain.user.dto.UserDto;
import com.mindshare.core.domain.user.dto.UserInfoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @Getter
    public static class Join extends UserDto {
        @NotNull
        @Valid
        private UserInfoDto userInfo;
    }

    @Getter
    public static class Update {
        @NotNull
        private UserTypes userType;

        @NotNull
        @Valid
        private UserInfoDto userInfo;
    }

}
