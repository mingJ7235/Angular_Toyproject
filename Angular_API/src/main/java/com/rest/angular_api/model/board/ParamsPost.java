package com.rest.angular_api.model.board;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 게시물 등록/수정시 입력 파라미터를 받기위한 DTO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class ParamsPost {

    @NotEmpty
    @Size (min=2, max= 50)
    //swagger 처리를위해 ApiModelProperty 작성
    @ApiModelProperty(value = "작성자명", required = true)
    private String author;

    @NotEmpty
    @Size (min = 2, max = 100)
    @ApiModelProperty (value = "제목", required = true)
    private String title;

    @Size (min = 2, max = 500)
    @ApiModelProperty (value = "내용", required = true)
    private String content;
}
