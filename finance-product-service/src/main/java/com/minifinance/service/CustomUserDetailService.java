package com.minifinance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.minifinance.model.Users;
import com.minifinance.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> res = repo.findById(username);
		if (res.isPresent()) {
			Users u = res.get();
			return User.builder().username(u.getUsername()).password(u.getPassword()).roles(u.getRole().toUpperCase())
					.build();
		}
		throw new UsernameNotFoundException("Invalid Credentials");
	}

}
