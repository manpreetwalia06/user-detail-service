package com.example.userdetailservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userdetailservice.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

}
