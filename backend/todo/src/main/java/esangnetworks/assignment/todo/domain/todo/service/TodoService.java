package esangnetworks.assignment.todo.domain.todo.service;

import esangnetworks.assignment.todo.domain.todo.dto.NewTodoRequestDto;
import esangnetworks.assignment.todo.domain.todo.dto.ReadTodoResponseDto;
import esangnetworks.assignment.todo.domain.todo.dto.UpdateTodoRequestDto;
import esangnetworks.assignment.todo.domain.todo.entity.Todo;
import esangnetworks.assignment.todo.domain.todo.repository.TodoRepository;
import esangnetworks.assignment.todo.domain.user.entity.User;
import esangnetworks.assignment.todo.domain.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final TodoRepository todoRepository;
  private final UserRepository userRepository;

  private final String USER = "USER";
  private final String ADMIN = "ADMIN";

  /**
   * 할 일 추가
   * @param newTodoRequestDto 제목, 내용, 날짜 포함
   * @param request 서블릿 요청 객체
   */
  public void addTodo (NewTodoRequestDto newTodoRequestDto, HttpServletRequest request) {

    Integer userId = getUserId(request);
    User user = userRepository.findById(userId).orElseThrow();
    //현재 로그인된 사용자 객체를 가져옴

    if (user.getRole().equals(USER)) {
      Todo todo = Todo.builder()
          .title(newTodoRequestDto.getTitle())
          .content(newTodoRequestDto.getContent())
          .user(user)
          .createdAt(LocalDate.now())
          .dueDate(newTodoRequestDto.getDueDate())
          .build();

      todoRepository.save(todo);
    }

  }

  /**
   * 할 일 목록 조회
   * @param size 한 페이지에 조회할 할 일 갯수
   * @param page 조회할 페이지
   * @param request 서블릿 요청 객체
   * @return 할 일 리스트
   */
  public List<ReadTodoResponseDto> readTodoList(int size, int page, HttpServletRequest request) {

    Pageable pageable = PageRequest.of(page, size, Direction.ASC, "dueDate"); //페이징 객체

    String role = getUserRole(request);
    Integer userId = getUserId(request);

    List<Todo> todoListEntity = new ArrayList<>();
    if (role.equals(ADMIN)) {
      todoListEntity = todoRepository.findAll(pageable).getContent();
      //관리자는 전체 할 일 조회 가능
    } else if (role.equals(USER)) {
      todoListEntity = todoRepository.findAllByUserUserId(pageable, userId);
      //사용자는 자신이 작성한 할 일만 조회 가능
    }
    else { //세션에 저장된 권한이 없거나 다른 권한인 경우
      throw new NoSuchElementException();
    }

    return todoListEntity.stream()
        .map(e -> e.toDto())
        .collect(Collectors.toList());

  }

  /**
   * 할 일 상세 조회
   * @param todoId 조회할 할 일 번호
   * @param request 서블릿 요청 객체
   * @return 할 일 상세 정보
   * @throws NoPermissionException
   */
  public ReadTodoResponseDto readTodoDetail (Integer todoId, HttpServletRequest request)
      throws NoPermissionException {
    ReadTodoResponseDto responseDto = todoRepository.findById(todoId)
        .map(todo -> todo.toDto())
        .orElseThrow();

    System.out.println(responseDto.getUser().getUserId());
    System.out.println(getUserId(request));
    System.out.println(getUserRole(request));

    if (getUserRole(request).equals(ADMIN)) {
      //관리자는 사용자들이 작성한 할 일의 조회만 가능
      responseDto.setReadOnly(true);
    }

    else responseDto.setReadOnly(false);

    return responseDto;

  }

  /**
   * 할 일 수정
   * @param updateTodoRequestDto 수정할 제목, 내용과 할 일 번호
   * @param request 서블릿 요청 객체
   */
  @Transactional //JPA 더티 체킹
  public void updateTodo (UpdateTodoRequestDto updateTodoRequestDto, HttpServletRequest request) {
    Integer userId = getUserId(request);

    todoRepository.findById(updateTodoRequestDto.getTodoId())
        .ifPresent(todo -> {
          if (todo.getUser().getUserId().equals(userId)) { //작성자만 수정 가능
            todo.setTitle(updateTodoRequestDto.getTitle());
            todo.setContent(updateTodoRequestDto.getContent());
        }});
  }


  /**
   * 할 일 삭제
   * @param todoId 삭제할 할 일 번호
   * @param request 서블릿 요청 객체
   */
  @Transactional //JPA 더티 체킹
  public void deleteTodo (Integer todoId, HttpServletRequest request) {
    Integer userId = getUserId(request);

    todoRepository.findById(todoId).ifPresent(todo-> {
      if(todo.getUser().getUserId().equals(userId)) //작성자만 삭제 가능
        todoRepository.deleteById(todoId);
    });
  }

  /**
   * 세션에서 사용자 정보(userId) 추출
   * @param request 서블릿 요청 객체
   * @return userId
   */
  public Integer getUserId (HttpServletRequest request) {
    return (Integer) request.getSession().getAttribute("userId");
  }

  /**
   * 세션에서 사용자 정보(role) 추출
   * @param request 서블릿 요청 객체
   * @return role
   */
  public String getUserRole (HttpServletRequest request) {
    return (String) request.getSession().getAttribute("role");
  }


}
