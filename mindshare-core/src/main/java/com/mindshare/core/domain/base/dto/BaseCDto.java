package com.mindshare.core.domain.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@Getter
public abstract class BaseCDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createUserId;

}
