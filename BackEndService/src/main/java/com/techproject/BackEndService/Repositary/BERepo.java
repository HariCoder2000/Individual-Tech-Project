package com.techproject.BackEndService.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techproject.BackEndService.entity.User;

@Repository
public interface BERepo extends JpaRepository<User, Integer> {

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.validIpAddress = :validIpAddress")
	boolean findByValidIpAddress(String validIpAddress);
}