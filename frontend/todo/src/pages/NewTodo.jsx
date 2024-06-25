import { useEffect, useState } from 'react';
import React from 'react';
import { Button, Input, InputLeftAddon, Flex, InputGroup, Textarea } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';



const NewTodo = () => {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [dueDate, setDueDate] = useState("");
    const [today, setToday] = useState("");
    const navigate = useNavigate();
    
    const handleTitle = (e) => {
        setTitle(e.target.value);
    }

    const handleContent = (e) => {
        setContent(e.target.value);
    }

    const handleDueDate = (e) => {
      setDueDate(e.target.value);
  }

  useEffect (() => {
    const today = new Date();
    const year = today.getFullYear();
    const month = (today.getMonth() + 1).toString().padStart(2, '0');
    const day = today.getDate().toString().padStart(2, '0');

    setToday(`${year}-${month}-${day}`);

  },[]);

  const handleSubmit = async () => {
    if(title.length ===0 || content.length ===0 || dueDate.length ===0) {
      alert("모든 칸을 입력해주세요");
      return;
    }

    await fetch("http://localhost:8080/todo",
        {
            method: "POST",
            body : JSON.stringify({
                title: title,
                content: content,
                dueDate : dueDate
              }),
            credentials: 'include', 
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then( window.location.href = '/todoList');
  };

  return (
    <div>
      <h1 style={{textAlign:'center'}}>새로운 할 일 만들기</h1>
        <InputGroup style={{margin:'20px'}}>
          <InputLeftAddon>제목</InputLeftAddon>
            <Input onChange={handleTitle}/>
        </InputGroup>

        <InputGroup style={{margin:'20px'}}>
          <Textarea placeholder="내용을 입력하세요" rows="10" cols="50" onChange={handleContent}></Textarea>
        </InputGroup>

        <InputGroup style={{margin:'20px'}}>
          <InputLeftAddon>마감 기한</InputLeftAddon>
          <Input size='md' min={today} type='date'onChange={handleDueDate} />
        </InputGroup>

        <Button style={{margin:'20px'}}  onClick={()=>navigate('/todoList')} colorScheme='gray'>목록으로</Button>
        <Button style={{margin:'20px'}}  onClick={handleSubmit} colorScheme='orange'>만들기</Button>
    </div>
  );
};

export default NewTodo;