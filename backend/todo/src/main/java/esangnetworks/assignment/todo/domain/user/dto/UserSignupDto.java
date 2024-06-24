package esangnetworks.assignment.todo.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserSignupDto {

  @NotNull(message = "이름을 입력하세요")
  @Length(max = 10)
  private String name;

  @NotNull(message = "아이디를 입력하세요")
  @Length(max=15)
  private String loginId;

  @NotNull(message = "비밀번호를 입력하세요")
  @Length(max=30)
  private String loginPw;


}
