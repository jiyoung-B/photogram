package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id, 
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, // 꼭 @valid가 적혀있는 파라미터 바로 뒤에 적어야됨
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
//				System.out.println("=========");
//				System.out.println(error.getDefaultMessage());
//				System.out.println("=========");
			}
			
			throw new CustomValidationApiException("유효성검사 실패함", errorMap);
		}else {
			System.out.println(userUpdateDto);
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity); // 세션 정보 변경
			return new CMRespDto<>(1, "회원수정완료", userEntity); 
			// 메세지컨버터라는 애가 응답시에 userEntity의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답
			// (user가 image 호출하고, image가 user 호출 무한반복)
			
		}
			
		
		
		
	}

}
