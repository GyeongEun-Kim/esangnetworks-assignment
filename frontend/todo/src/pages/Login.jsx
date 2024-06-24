import { Button } from '@chakra-ui/react';
import React, { useState } from 'react';
import { Input, Stack, InputLeftAddon, InputGroup } from '@chakra-ui/react';
import '../styles/Signup.css';

const Login = () => {
  
  const [loginId, setLoginId] = useState("");
  const [loginPw, setLoginPw] = useState("");

  const handleIdChange = (e) => {
    const value = e.target.value;
    setLoginId(value);
  };

  const handlePwChange = (e) => {
    const value = e.target.value;
    setLoginPw(value);
  };

  const login = async () => {
    if(loginId.length===0 || loginPw.length ===0) {
      alert("아이디와 비밀번호를 입력해주세요");
      return ;
    }

    await fetch("http://localhost:8080/login",
        {
            method: "POST",
            body : JSON.stringify({
                loginId: loginId,
                loginPw: loginPw,
              }),
            credentials: 'include', 
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then(response => {
          if (response.status === 200) 
            window.location.href = '/'
          else if (response.status === 500)
            alert("아이디 또는 비밀번호가 일치하지 않습니다.");
        })
        
  }


  return (
    <div className="signup-container">
      <Stack>
        <h1 style={{textAlign:'center'}}>로그인</h1>
        <Stack spacing={3}>
        <InputGroup>
        <InputLeftAddon>아이디</InputLeftAddon>
          <Input
            id="loginId"
            value={loginId}
            onChange={handleIdChange}
          />
        </InputGroup>
        <InputGroup>
        <InputLeftAddon>비밀번호</InputLeftAddon>
          <Input
            type="password"
            id="loginPw"
            value={loginPw}
            onChange={handlePwChange}
          />
        </InputGroup>
        
        <Button onClick={login}>로그인</Button>
        </Stack>
        </Stack>
    </div>
  );
};

export default Login;
