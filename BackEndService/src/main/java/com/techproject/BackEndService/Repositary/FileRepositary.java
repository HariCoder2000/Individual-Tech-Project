package com.techproject.BackEndService.Repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techproject.BackEndService.entity.Files;

@Repository
public interface FileRepositary extends JpaRepository<Files, Integer> {
	  Optional<Files> findById(Long id);

}