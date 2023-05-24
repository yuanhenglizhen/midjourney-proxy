package com.github.novicezk.midjourney.dto;

import com.github.novicezk.midjourney.enums.Action;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("回调参数")
@Data
public class NotifyDTO {

	private Action action;
	private  String id ;
	private String prompt ;
	private String promptEn ;
	private String description ;
	private String state ;
	private Long submitTime ;
	private Long startTime ;
	private Long finishTime ;
	private String imageUrl ;
	private String status ;
	private String failReason ;
}
