import {React, useEffect, useState} from 'react'
import { Button, Flex, Spacer } from '@chakra-ui/react'
import { useNavigate } from "react-router-dom"

const Index = () => {
    const navigate = useNavigate();
    const [cookie, setCookie] = useState(document.cookie);

    const logout = () => {
      document.cookie = 'mySession =; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
      document.cookie = 'JSESSIONID =; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
      window.location.href = '/';
    }

    useEffect(() => {
        setCookie(document.cookie);
      }, [document.cookie]);
    
    return (
        <>
          <div className="container">
            <Flex direction='column'>
            <h1>할일 목록 작성 프로그램</h1>

            <Flex style={{margin:'80px'}}>
            {!document.cookie ?
            <>
                <Button size='lg' colorScheme='orange' onClick={()=>navigate('/login')} >로그인</Button>
                <Spacer/>
                <Button size='lg' colorScheme='orange' onClick={()=>navigate('/signup')}>회원가입</Button>
            </>
                :
            <>
                <Button size='lg' colorScheme='orange' onClick={()=>navigate('/todoList')}>투두리스트 </Button>
                <Spacer/>
                <Button size='lg' colorScheme='orange' onClick={logout}>로그아웃</Button>
            </>}
            </Flex>
            </Flex>
          </div>      
        </>
      )
}

export default Index