package esangnetworks.assignment.todo.domain.todo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
/**
 * 할일 목록을 조회하는 response에서 사용하는 DTO
 */
public class TodoListPagingDto {

  private Integer totalPages; //todo갯수와 size로 totalPages가 결정됨

  private List<ReadTodoResponseDto> todoList; //todo리스트

}
