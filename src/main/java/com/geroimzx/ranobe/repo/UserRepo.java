package com.geroimzx.ranobe.repo;

import com.geroimzx.ranobe.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
