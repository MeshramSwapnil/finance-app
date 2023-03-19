package com.minifinance.model;

import com.minifinance.constants.ErrorCodesConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

	@NotBlank
	@Builder.Default
	private long errorcode = ErrorCodesConstants.NO_ERROR;
	@NotBlank
	private String message;
	private Boolean status;

	private Object data;

	
}
