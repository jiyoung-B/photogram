package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		// save할때는 subscribe 객체가 필요한데, int fromUserId와 int toUserId는 int 값이어서 
		// subscribe 객체를 못 만든다.( User fromUser, User toUser : Object)
		// 그래서 native 쿼리를 쓰는게 더 쉽고 간단하게 구현할 수 있다.
		// 왜? userRepository에서 findById로 User Object 받아서 만들어야하는데 그게 더 어려웡
		// subscribeRepository.save(null) x
		// int result = subscribeRepository.mSubscribe(fromUserId, toUserId);
		// => int로 받지말고 void로 변경(repository도 변경)
		// 나중에 터지면 try-catch로 Exception 처리하는게 나음
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}
		catch(Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
			
		}
		
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}

}
