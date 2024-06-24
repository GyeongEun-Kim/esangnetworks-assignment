package esangnetworks.assignment.todo.domain.user.repository;

import esangnetworks.assignment.todo.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByLoginId (String loginId);
  Optional<User> findByLoginIdAndLoginPw (String loginId, String loginPw);
}
