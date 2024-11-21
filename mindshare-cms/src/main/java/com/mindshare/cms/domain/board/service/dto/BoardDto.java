package com.mindshare.cms.domain.board.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mindshare.core.domain.base.dto.BaseCUDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BoardDto extends BaseCUDto {

    @NotEmpty
    @Size(max = 100)
    private String title;

    @NotEmpty
    @Size(max = 4000)
    private String content;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer viewCount;

    @Getter
    public static class Request extends BoardDto {

    }

    @Getter
    public static class Response extends BoardDto {
        private Long id;
    }

    @Getter
    @Setter
    public static class Search {
        private String title;
    }

}
