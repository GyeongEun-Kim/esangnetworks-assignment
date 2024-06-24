package esangnetworks.assignment.todo.domain.user.controller;

import esangnetworks.assignment.todo.domain.user.dto.UserLoginDto;
import esangnetworks.assignment.todo.domain.user.service.UserService;
import esangnetworks.assignment.todo.domain.user.dto.UserSignupDto;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * 회원 가입(일반 사용자만 가능)
   * @param userSignupDto 이름, 아아디, 비밀번호 포함
   */
  @PostMapping("/signup")
  public ResponseEntity signup (@RequestBody @Validated UserSignupDto userSignupDto) {
    userService.signup(userSignupDto);
    return ResponseEntity.ok().build();
  }

  /**
   * 아이디 중복 체크
   * @param loginId 체크할 아이디
   * @return 중복 여부 (중복 : true, 중복x : false)
   */
  @GetMapping("/check/{loginId}")
  public ResponseEntity<Map<String,Boolean>> checkAvailability (@PathVariable String loginId)  {
    Map<String, Boolean> response = new HashMap<>();

    response.put("isAvailable", userService.checkAvailability(loginId));

    return ResponseEntity.ok(response);

  }

  /**
   * 로그인
   * @param userLoginDto 아이디, 비밀번호
   * @param request 서블릿 요청 객체
   * @param response 서블릿 응답 객체
   */
  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Validated UserLoginDto userLoginDto,HttpServletRequest request, HttpServletResponse response)
      throws ServletException {

    response.addCookie(userService.login(userLoginDto, request));
    return ResponseEntity.ok().build();

  }

  /**
   * 로그아웃
   * @param request 서블릿 요청 객체
   * @throws ServletException
   */
  @PostMapping("/logout")
  public ResponseEntity logout (HttpServletRequest request) throws ServletException {
    userService.logout(request);
    return ResponseEntity.ok().build();
  }



}
