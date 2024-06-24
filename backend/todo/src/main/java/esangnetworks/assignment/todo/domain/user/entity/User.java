package esangnetworks.assignment.todo.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Column(nullable = false, length = 10)
  private String name;

  @Column(nullable = false, length = 15)
  private String loginId;

  @Column(nullable = false, length = 30)
  private String loginPw;

  @Column(nullable = false, length = 5, updatable = false)
  private String role; //USER, ADMIN

}
