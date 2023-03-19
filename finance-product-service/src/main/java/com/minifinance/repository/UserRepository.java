package com.minifinance.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.minifinance.model.Users;

public interface UserRepository extends ListCrudRepository<Users, String> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE Users u SET u.refreshToken =:refreshToken WHERE u.username =:username")
	int updateRefreshToken(@Param("refreshToken") String refreshToken, @Param("username") String username);

}
