package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{
	
	
	// Jpa를 통해 save로 넣는게 아니고, 내가 직접만드는 거라 @PrePersist(LocalDateTime) 설정이 작동을 안해서 날짜를 넣어줘야 한다!
	// 날짜 value는 그냥 now() 함수로
	// 콤마하고, nativeQuery = true 라고 설정해야 발동을 함
	// 데이터베이스에 변경을 주는 쿼리에는 modifying이 필요하다(INSERT, DELETE, UPDATE를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!)
	@Modifying 
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, :now()) ", nativeQuery = true)
	// return 이 int인 이유 : 변경된 행의 개수가 리턴 => 10개 성공시 10, 실패시(오류) -1(변경된 행이 없다는것. insert가 안되었다는 것), 변경된 행 없으면 0(?) (preparedStatement 정해져있음(?))
	// int mSubscribe(int fromUserId, int toUserId); // m은 내가 만들었다는 뜻의 약어 
	void mSubscribe(int fromUserId, int toUserId); // return int 에서 void로 변경
	// void로 변경 이유 => 만약에 데이터베이스에서 오류나면 Exception 발생, service에서 try-catch로 예외 던져서 
	// 그냥 오류나면 전부다 customExceptionHandler에서 처리하려고. 옛날에는 return int 많이 했고 요새는 할 필요없다고 함.
	
	
	// :은 변수를 바인딩해서 넣겠다는 문법.
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void mUnSubscribe(int fromUserId, int toUserId);
	
	// 구독여부(ssar(1)로 로그인, cos(2)페이지로 감)
	// select count(*) from subscribe where fromUserId = 1 and toUserId = 2;
	@Query(value= "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId :pageUserId", nativeQuery =true)
	int mSubscribeState(int principalId, int pageUserId);
	
	@Query(value= "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery =true)
	int mSubscribeCount(int pageUserId);
	

}
