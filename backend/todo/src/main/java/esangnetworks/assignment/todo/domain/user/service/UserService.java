package esangnetworks.assignment.todo.domain.user.service;

import esangnetworks.assignment.todo.domain.user.dto.UserLoginDto;
import esangnetworks.assignment.todo.domain.user.dto.UserSignupDto;
import esangnetworks.assignment.todo.domain.user.entity.User;
import esangnetworks.assignment.todo.domain.user.repository.UserRepository;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final String SIGNUP_ROLE = "USER";


  /**
   * 회원 가입
   * @param signupDto 이름, 아이디, 비밀번호 포함
   */
  public void signup (UserSignupDto signupDto) {
    userRepository.save(User.builder()
        .name(signupDto.getName())
        .loginId(signupDto.getLoginId())
        .loginPw(signupDto.getLoginPw())
        .role(SIGNUP_ROLE) //서비스를 통해 회원가입할 경우 무조건 USER, 관리자는 따로 가입한다고 가정
        .build());
  }


  /**
   * 아이디 중복 조회
   * @param loginId 체크할 아이디
   * @return 사용가능-> true, 사용불가-> false
   */
  public boolean checkAvailability (String loginId) {
    Optional<User> optionalUser = userRepository.findByLoginId(loginId);

    if(optionalUser.isPresent()) return false; //아이디가 중복인 경우
    else return true; //아이디가 중복이 아닌 경우
  }

  /**
   * 로그인
   * @param loginDto 아이디, 비밀번호
   * @param request 서블릿 요청 객체
   * @return
   * @throws Exception
   */
  public Cookie login(UserLoginDto loginDto, HttpServletRequest request) throws ServletException {
    User user = userRepository.findByLoginIdAndLoginPw(loginDto.getLoginId(), loginDto.getLoginPw())
        .orElseThrow( ()-> new SecurityException("아이디 또는 비밀번호가 일치하지 않습니다.")
    );

    HttpSession session = request.getSession();

    session.setAttribute("userId", user.getUserId());
    session.setAttribute("name", user.getName());
    session.setAttribute("role", user.getRole());

    Cookie cookie = new Cookie("mySession", session.getId());

    return cookie;
  }


  /**
   * 로그아웃
   * @param request 서블릿 요청 객체
   * @throws ServletException
   */
  public void logout(HttpServletRequest request) throws ServletException {
    request.logout();
  }

}
