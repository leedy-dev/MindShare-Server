package com.mindshare.cmm.domain.user.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mindshare.cmm.common.enums.UserTypes;
import com.mindshare.core.domain.base.dto.BaseCUDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserDto extends BaseCUDto {

    @NotNull
    private UserTypes userType;

    @NotEmpty
    @Size(max = 30)
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Size(max= 20)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastLoginDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean accountExpired = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean credentialExpired = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean locked = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled = true;

    @Getter
    public static class Response extends UserDto {
        private Long id;

        private UserInfoDto userInfo;
    }

}
