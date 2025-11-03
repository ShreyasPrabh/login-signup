package com.shreyas.pom.repositry;

import com.shreyas.pom.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositry extends JpaRepository<User,Long> {

     User findByEmail(String email);
}
