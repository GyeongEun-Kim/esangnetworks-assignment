package esangnetworks.assignment.todo.domain.todo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TodoPagingDto {

  private Integer totalPages;
  private List<ReadTodoResponseDto> todoList;

}
