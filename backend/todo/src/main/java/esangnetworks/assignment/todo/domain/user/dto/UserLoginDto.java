package esangnetworks.assignment.todo.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

  @NotNull(message = "아이디를 입력하세요")
  private String loginId;

  @NotNull(message = "비밀번호를 입력하세요")
  private String loginPw;


}
