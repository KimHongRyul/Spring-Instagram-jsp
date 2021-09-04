package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

// NotNull : null 값 체크
// NotEmpty : 빈값 + null 체크
// NotBlank : 빈값 + null + 공백 체크
@Data
public class CommentDto {
	@NotBlank
	private String content;
	@NotNull
	private Integer imageId;
	//to Entity가 필요없다.
}
