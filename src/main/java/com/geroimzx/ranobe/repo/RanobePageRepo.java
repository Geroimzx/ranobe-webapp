package com.geroimzx.ranobe.repo;

import com.geroimzx.ranobe.model.RanobePage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RanobePageRepo extends CrudRepository<RanobePage, Long> {
    RanobePage findByName(String name);
}
