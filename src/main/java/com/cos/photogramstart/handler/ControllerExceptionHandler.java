package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

	
	@ExceptionHandler(CustomValidationException.class)
	//public CMRespDto<?> validationException(CustomValidationException e) {
		public String validationException(CustomValidationException e) {
		//return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());
		
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할 때는 Script 좋음.
		// 2. Ajax통신 - CMRespDto
		// 3. Android 통신 - CMRespDto
		return Script.back(e.getErrorMap().toString());
	}

}
