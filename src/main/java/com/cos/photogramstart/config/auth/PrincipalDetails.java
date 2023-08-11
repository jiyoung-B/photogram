package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private User user;
	public PrincipalDetails(User user) {
		this.user = user;
	}

	
	// 권한 : 한개가 아닐 수 있음.(3개 이상의 권한)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collector = new ArrayList<>();
		
		collector.add(() ->{return user.getRole();});
		
		
//		collector.add(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				// TODO Auto-generated method stub
//				return user.getRole();
//			}
//		});
		return collector;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
