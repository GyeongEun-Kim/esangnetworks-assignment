import React, { useState } from 'react';
import { Button,InputLeftAddon, InputGroup, Input, Textarea, RadioGroup, Stack, Radio } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';

const TodoEdit = ({ todo, setIsEditing }) => {
  const [title, setTitle] = useState(todo.title);
  const [content, setContent] = useState(todo.content);
  const [done, setDone] = useState(todo.done);
  const [dueDate, setDueDate] = useState(`${todo.dueDate}T00:00:00`);

  const navigate = useNavigate();


  const handleDueDate = (e) => {
    setDueDate(e.target.value);
  }
  const handleTitle = (e) => {
    setTitle(e.target.value);
  }

  const handleContent = (e) => {
    setContent(e.target.value);
  }

  const handleDone = (e) => {
    setDone(e.target.value);
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
        body: JSON.stringify({ todoId: todo.todoId, title, content , dueDate, done}),
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

        <RadioGroup defaultValue='false' style={{margin:'20px'}}>
          <Stack spacing={5} direction='row'>
            <Radio colorScheme='red' value='true' onChange={handleDone}>
              완료
            </Radio>
            <Radio colorScheme='orange' value='false' onChange={handleDone}>
              미완료
            </Radio>
          </Stack>
        </RadioGroup>

        <Button style={{margin:'20px'}}  onClick={handleSubmit} colorScheme='orange'>수정하기</Button>

      <Button onClick={() => setIsEditing(false)}>취소</Button>
    </>
  );
};

export default TodoEdit;
