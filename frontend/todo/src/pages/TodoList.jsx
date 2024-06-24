import { TableContainer, Table, Thead, Th, Tr, Tbody, Spacer, Button, Flex } from '@chakra-ui/react';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const TodoList = () => {
  const [todos, setTodos] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [totalPages, setTotalPages] = useState();
  const navigate = useNavigate();

  

  useEffect(() => {
    fetchTodos(page, size);
  }, [page, size]);

  const fetchTodos = async (page, size) => {
    const response = await fetch(`http://localhost:8080/todo?page=${page}&size=${size}`,
    {
      credentials: 'include', 
    })
    .then(res=>res.json())
    .then(res=> {  
      setTodos(res.todoList);
      setTotalPages(res.totalPages);
    })
  };

  const handleTodoClick = (todoId) => {
    navigate(`/todoList/${todoId}`);
  };

  const handleNewTodoClick = () => {
    navigate('/todoList/new');
  };

  return (
    <>
    <div style={{width:'50rem'}} >
    <h1 style={{textAlign:'center', margin:'50px'}}>할 일 목록</h1>
    <div style={{textAlign:'end', margin:'30px'}}>
    <Flex>
    <Button onClick={()=> navigate('/')} colorScheme='orange'>메인으로</Button>
    <Spacer/>
    <Button onClick={handleNewTodoClick} colorScheme='orange'>새 투두 생성하기</Button>
    </Flex>
    </div>
      <TableContainer>
      <Table variant='simple' style={{width:'50rem'}}>
      <Thead>
      <Tr>
      <Th>마감 기한</Th>
      <Th>완료 여부</Th>
        <Th>제목</Th>
        <Th>작성자</Th>
      </Tr>
    </Thead>
    <Tbody>
        {todos.map(todo => (
          <Tr key={todo.id} onClick={() => handleTodoClick(todo.todoId)}>
            <Th>{todo.dueDate}</Th>
            {todo.done? <Th style={{color:'green'}}>완료</Th> : <Th style={{color:'red'}}>미완료</Th>}
            <Th>{todo.title}</Th>
            <Th>{todo.user.name}</Th>
          </Tr>
        ))}
    </Tbody>
    </Table>
      </TableContainer>
      <Flex justify='center'>
        <Button onClick={() => setPage(page > 0 ? page - 1 : 0)} style={{margin:'30px'}}>이전</Button>
        <Button onClick={() => setPage(page + 1 < totalPages? page+1 : page)} style={{margin:'30px'}}>다음</Button>
      </Flex>
      
    </div>
    </>
  );
}

export default TodoList;
