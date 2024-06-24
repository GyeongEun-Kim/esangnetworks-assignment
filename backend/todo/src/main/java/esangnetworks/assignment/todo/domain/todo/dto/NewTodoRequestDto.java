package esangnetworks.assignment.todo.domain.todo.dto;

import java.time.LocalDate;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class NewTodoRequestDto {

  @NotNull(message = "제목을 필수로 입력해야합니다.")
  @Length(max = 50, message = "최대 글자수(50자)를 초과하였습니다.")
  private String title;

  @NotNull(message = "내용을 필수로 입력해야합니다.")
  @Length(max = 500, message = "최대 글자수(500자)를 초과하였습니다.")
  private String content;

  @NotNull(message = "마감기한을 필수로 입력해야합니다.")
  @Future(message = "마감기한은 현재 이후의 날짜여야 합니다.")
  private LocalDate dueDate;


}
