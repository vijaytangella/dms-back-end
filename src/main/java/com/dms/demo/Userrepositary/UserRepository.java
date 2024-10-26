package com.dms.demo.Userrepositary;



import org.springframework.data.jpa.repository.JpaRepository;
import com.dms.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
