package com.rest.angular_api.model.reply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ParamsReply {

    @NotEmpty
    @Size (min = 2, max = 50)
    @ApiModelProperty (value = "댓글 작성자명", required = true)
    private String author;

    @NotEmpty
    @Size (min = 2, max = 500)
    @ApiModelProperty (value = "댓글 내용", required = true)
    private String content;

    @ApiModelProperty (value = "부모 댓글 아이디 값")
    private Long parentReplyId;

}
