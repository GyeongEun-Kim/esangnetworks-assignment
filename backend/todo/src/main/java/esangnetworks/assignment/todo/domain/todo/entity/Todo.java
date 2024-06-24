package esangnetworks.assignment.todo.domain.todo.entity;

import esangnetworks.assignment.todo.domain.todo.dto.ReadTodoResponseDto;
import esangnetworks.assignment.todo.domain.user.dto.UserInfoDto;
import esangnetworks.assignment.todo.domain.user.entity.User;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer todoId;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(nullable = false, length = 500)
  private String content;

  @Column(nullable = false)
  private LocalDate createdAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  private LocalDate dueDate;

  @Column(nullable = false)
  private Boolean done;

  public ReadTodoResponseDto toDto () {
    return ReadTodoResponseDto.builder()
        .todoId(todoId)
        .title(title)
        .content(content)
        .dueDate(dueDate)
        .user(new UserInfoDto(user.getName(), user.getUserId()))
        .done(done)
        .build();
  }

}
