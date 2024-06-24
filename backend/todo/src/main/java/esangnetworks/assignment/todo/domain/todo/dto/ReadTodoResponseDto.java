package esangnetworks.assignment.todo.domain.todo.dto;

import esangnetworks.assignment.todo.domain.user.dto.UserInfoDto;
import esangnetworks.assignment.todo.domain.user.entity.User;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
/**
 * 할 일 조회 response에 사용하는 DTO
 */
public class ReadTodoResponseDto {
  private Integer todoId;
  private String title;
  private String content;
  private LocalDate dueDate;
  private UserInfoDto user;
  private Boolean readOnly; //요청한 사용자의 권한에 따라 조회만 가능하도록 구별하기 위한 변수

  private Boolean done;

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }


}
