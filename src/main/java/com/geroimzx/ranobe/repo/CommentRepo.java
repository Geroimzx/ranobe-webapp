package com.geroimzx.ranobe.repo;

import com.geroimzx.ranobe.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {
}
