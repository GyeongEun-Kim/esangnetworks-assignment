import React, { useState } from 'react';
import { Button, Flex, InputLeftAddon, InputGroup, Input, Textarea } from '@chakra-ui/react';

const TodoEdit = ({ todo, setIsEditing }) => {
  const [title, setTitle] = useState(todo.title);
  const [content, setContent] = useState(todo.content);
  const [dueDate, setDueDate] = useState(`${todo.dueDate}T00:00:00`);


  const handleDueDate = (e) => {
    setDueDate(e.target.value);
  }
  const handleTitle = (e) => {
    setTitle(e.target.value);
  }

  const handleContent = (e) => {
    setContent(e.target.value);
  }


    const handleSubmit = async () => {
      if(title.length ===0 || content.length ===0 || dueDate.length ===0) {
        alert("모든 칸을 입력해주세요");
        return;
      }

      const response = await fetch(`http://localhost:8080/todo`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({ todoId: todo.todoId, title, content , dueDate}),
      })
      .then(response=> console.log(response));
      setIsEditing(false);
    
  };

  return (
  <>
      <h1 style={{textAlign:'center'}}>할 일 수정하기</h1>
        <InputGroup style={{margin:'20px'}}>
          <InputLeftAddon>제목</InputLeftAddon>
            <Input value={title} onChange={handleTitle}/>
        </InputGroup>

        <InputGroup style={{margin:'20px'}}>
          <Textarea value={content} rows="10" cols="50" onChange={handleContent}></Textarea>
        </InputGroup>

        <InputGroup style={{margin:'20px'}}>
          <InputLeftAddon>마감 기한</InputLeftAddon>
          <Input value={dueDate} size='md' type='datetime-local'onChange={handleDueDate} />
        </InputGroup>

        <Button style={{margin:'20px'}}  onClick={handleSubmit} colorScheme='orange'>수정하기</Button>

      <Button onClick={() => setIsEditing(false)}>취소</Button>
    </>
  );
};

export default TodoEdit;
