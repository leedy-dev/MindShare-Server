package com.mindshare.ams.domain.user.service.dto;

import com.mindshare.cmm.domain.user.service.dto.UserDto;
import com.mindshare.cmm.domain.user.service.dto.UserInfoDto;
import com.mindshare.cmm.common.enums.UserTypes;
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
