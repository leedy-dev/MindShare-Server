package com.mindshare.core.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mindshare.core.common.validate.PatternDefine;
import com.mindshare.core.domain.base.dto.BaseCUDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserDto extends BaseCUDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dtype;

    @NotEmpty
    @Size(max = 30)
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Pattern(regexp = PatternDefine.PASSWORD_NUMBER_PATTERN)
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
    public static class Join extends UserDto {
        @NotNull
        @Valid
        private UserInfoDto userInfo;
    }

    @Getter
    public static class Response extends UserDto {
        private Long id;

        private UserInfoDto userInfo;
    }

}
