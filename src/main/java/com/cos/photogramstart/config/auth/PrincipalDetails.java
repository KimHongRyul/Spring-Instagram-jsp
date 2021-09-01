package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{
	
	private User user;
	private static final long serialVersionUID = 1L;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	// 권한 : 한개가 아닐 수 있음 (3개 이상의 권한)
	@Override // 권한을 가지고 오는 함수(Role)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//arrayList의 부모가 컬렉션임
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> {return user.getRole();});
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override // 계정이 만료 되었나?
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override // 계정이 잠겼나?
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override // 계정의 비밀번호를 오랜시간 바꾸지 않았나?
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override // 계정이 활성화 되었나? (휴면 계정)
	public boolean isEnabled() {
		return true;
	}

}
