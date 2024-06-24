package esangnetworks.assignment.todo.domain.todo.controller;

import esangnetworks.assignment.todo.domain.todo.dto.TodoPagingDto;
import esangnetworks.assignment.todo.domain.todo.dto.UpdateTodoRequestDto;
import esangnetworks.assignment.todo.domain.todo.dto.NewTodoRequestDto;
import esangnetworks.assignment.todo.domain.todo.dto.ReadTodoResponseDto;
import esangnetworks.assignment.todo.domain.todo.service.TodoService;
import java.util.List;
import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
  private final TodoService todoService;

  /**
   * 할 일 추가
   * @param newTodoRequestDto 할 일의 제목, 내용 포함
   * @param request 서블릿 요청 객체
   */
  @PostMapping("")
  public ResponseEntity addTodo (@RequestBody @Validated NewTodoRequestDto newTodoRequestDto, HttpServletRequest request) {
    todoService.addTodo(newTodoRequestDto, request);
    return ResponseEntity.ok().build();
  }


  /**
   * 할 일 목록 조회
   * @param size 한 페이지에 조회할 할 일 갯수
   * @param page 조회할 페이지
   * @param request 서블릿 요청 객체
   * @return 할 일 리스트
   */
  @GetMapping("")
  public ResponseEntity<TodoPagingDto> readTodoList(@RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
    return ResponseEntity.ok(todoService.readTodoList(size, page, request));
  }

  /**
   * 할 일 상세 조회
   * @param todoId 조회할 할 일 번호
   * @param request 서블릿 요청 객체
   * @return 할 일 상세 데이터
   */
  @GetMapping("/{todoId}")
  public ResponseEntity<ReadTodoResponseDto> readTodoDetail (@PathVariable Integer todoId, HttpServletRequest request)
      throws NoPermissionException {
    return ResponseEntity.ok(todoService.readTodoDetail(todoId, request));
  }

  /**
   * 할 일 수정
   * @param updateTodoRequestDto 수정할 제목, 내용과 할 일 번호
   * @param request 서블릿 요청 객체
   */
  @PutMapping
  public ResponseEntity updateTodo (@RequestBody @Validated UpdateTodoRequestDto updateTodoRequestDto, HttpServletRequest request) {
    todoService.updateTodo(updateTodoRequestDto,request);
    return ResponseEntity.ok().build();
  }


  /**
   * 할 일 삭제
   * @param todoId 삭제할 할 일 번호
   * @param request 서블릿 요청 객체
   */
  @DeleteMapping("/{todoId}")
  public ResponseEntity deleteTodo (@PathVariable Integer todoId, HttpServletRequest request) {
    todoService.deleteTodo(todoId, request);
    return ResponseEntity.ok().build();
  }

}
