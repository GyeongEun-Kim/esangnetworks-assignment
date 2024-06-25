// Signup.jsx
import React, { useState } from 'react';
import '../styles/Signup.css';
import { Input, Stack, InputLeftAddon, InputGroup, Button, Flex } from '@chakra-ui/react';

const Signup = () => {

  const [name, setName] = useState("");
  const [loginId, setLoginId] = useState("");
  const [loginPw, setLoginPw] = useState("");
  const [loginPwCheck, setLoginPwCheck] = useState("");
  const [isIdAvailable, setIsIdAvailable] = useState(false);

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleIdChange = (e) => {
    setLoginId(e.target.value);
  };

  const handlePwChange = (e) => {
    setLoginPw(e.target.value);
  };

  const handlePwCheckChange = (e) => {
    setLoginPwCheck(e.target.value);
  };

  const handleIdCheck = async () => {
    await fetch(`http://localhost:8080/check/${loginId}`,
      {
          method: "GET",
          credentials: 'include', 
          headers: {
              "Content-Type": "application/json",
          },
      })
      .then(response=> response.json())
      .then(response => {
        if (response.isAvailable === true) {
          alert('사용 가능한 아이디입니다.');
          setIsIdAvailable(true);
        }
        else {
          alert('사용 불가능한 아이디입니다.');
        }
      })
  };

  const handleSubmit = () => {
    if (loginPw !== loginPwCheck) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }

    if(isIdAvailable === false) {
      alert('아이디 중복 확인을 해주세요');
      return ;
    }

    if(loginId.length===0 || loginPw.length ===0 || name.length===0) {
      alert("이름, 아이디,비밀번호를 입력해주세요");
      return ;
    }

    
    fetch("http://localhost:8080/signup",
      {
          method: "POST",
          body : JSON.stringify({
              name : name,
              loginId: loginId,
              loginPw: loginPw,
            }),
          credentials: 'include', 
          headers: {
              "Content-Type": "application/json",
          },
      })
  .then(response => {
      if (response.status === 200) {
        window.location.href = '/login'
      }
      else if (response.status === 500) {
        alert("회원가입에 실패했습니다.");
      }
    })
  };

  return (
    <div className="signup-container">
      <Stack>
        <h1 style={{textAlign:'center'}}>회원가입</h1>
        
        <InputGroup>
          <InputLeftAddon>이름</InputLeftAddon>
          <Input
            type="text"
            id="name"
            value={name}
            onChange={handleNameChange}
          />
        </InputGroup>

        <Flex>
        <InputGroup>
          <InputLeftAddon>아이디</InputLeftAddon>
          <Input
            id="loginId"
            value={loginId}
            onChange={handleIdChange}
          />
          </InputGroup>
          <Stack/>
          <Button onClick={handleIdCheck}>
            아이디 중복 확인
          </Button>
        </Flex>
        
        <InputGroup>
          <InputLeftAddon>비밀번호</InputLeftAddon>
          <Input
            type="password"
            id="loginPw"
            value={loginPw}
            onChange={handlePwChange}
          />
        </InputGroup>

        <InputGroup>
          <InputLeftAddon>비밀번호 확인</InputLeftAddon>
          <Input
            type="password"
            id="loginPwCheck"
            value={loginPwCheck}
            onChange={handlePwCheckChange}
          />
        </InputGroup>

        <Button onClick={handleSubmit}>회원가입</Button>
        </Stack>
    </div>
  );
};

export default Signup;
