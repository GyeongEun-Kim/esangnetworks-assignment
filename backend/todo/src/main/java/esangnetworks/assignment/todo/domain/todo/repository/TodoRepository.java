package esangnetworks.assignment.todo.domain.todo.repository;

import esangnetworks.assignment.todo.domain.todo.entity.Todo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
  Page<Todo> findAllByUserUserId (Pageable pageable, Integer userId);
}
