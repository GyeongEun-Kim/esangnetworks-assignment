import React, { useEffect, useState } from 'react';
import { Flex, Button , Spacer, Stack} from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import UpdateTodo from './UpdateTodo.jsx';

const TodoDetail = () => {
  const [todo, setTodo] = useState("");
  const navigate = useNavigate();
  const [isEditing, setIsEditing] = useState(false);

  // window.location.pathname을 사용하여 경로에서 ID 추출
  const path = window.location.pathname;
  const todoId = path.split('/').pop(); // 경로에서 마지막 부분을 추출하여 ID로 사용

  useEffect(() => {
      fetch(`http://localhost:8080/todo/${todoId}`,
      {
        credentials: 'include', 
      }
      )
      .then(res=> res.json())
      .then(res=>setTodo(res));
    }, [isEditing]);

  const handleDelete = async () => {
    await fetch(`http://localhost:8080/todo/${todoId}`,
        {
            method : "DELETE",
            credentials: 'include', 
        }
    )
    .then(response=> response.json())
    .then( window.location.replace('/todoList'));
  }

  return (
    <>
     <div>
    {todo !== "" ? (
      <>
     {isEditing ? (
        <UpdateTodo todo={todo} setIsEditing={setIsEditing} />
      ) : ( 
        <div>
          <Stack>
          <h1>{todo.title}</h1>
          <div style={{border:'black solid', borderRadius:'7px', padding:'15px'}}>
          <h2>{todo.content}</h2>
          </div>
          <Flex>
          <h2>마감기한 : {todo.dueDate}</h2>
          <Spacer/>
          <h2>작성자 : {todo.user.name}</h2>
          </Flex>
          {todo.done? <h2 style={{color:'green'}}>완료!</h2> : <h2 style={{color:'red'}}>미완료</h2>}
          <Flex style={{margin:'80px'}}>
          <Button size='lg' colorScheme='gray' onClick={() => navigate('/todoList')} >목록으로</Button>
          <Spacer/>

          {!todo.readOnly ? (<>
            <Button size='lg' colorScheme='orange' onClick={() => setIsEditing(true)} >수정하기</Button>
            <Spacer/>
            <Button size='lg' colorScheme='orange' onClick={handleDelete} >삭제하기</Button>
            </>)   :
             <></>}

            </Flex>
            
          
            </Stack>
        </div>
        )}</>
      ) : <></>}
    </div>


</>
  );
};

export default TodoDetail;
