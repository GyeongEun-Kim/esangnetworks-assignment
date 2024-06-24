package esangnetworks.assignment.todo.domain.todo.dto;

import java.time.LocalDate;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UpdateTodoRequestDto {

  @NotNull(message = "제목을 필수로 입력해야합니다.")
  @Length(max = 50, message = "최대 글자수(50자)를 초과하였습니다.")
  private String title;

  @NotNull(message = "내용을 필수로 입력해야합니다.")
  @Length(max = 500, message = "최대 글자수(500자)를 초과하였습니다.")
  private String content;

  @NotNull(message = "수정할 할 일의 번호가 없습니다.")
  private Integer todoId;

  @NotNull(message = "마감기한을 필수로 입력해야합니다.")
  @FutureOrPresent(message = "마감기한은 현재 이후의 날짜여야 합니다.")
  private LocalDate dueDate;

  private Boolean done;

}
