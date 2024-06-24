import './styles/App.css'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import Signup from './pages/Signup'
import Login from './pages/Login'
import Index from './pages/Index'
import TodoList from './pages/TodoList'
import NewTodo from './pages/NewTodo'
import TodoDetail from './pages/TodoDetail'
import UpdateTodo from './pages/UpdateTodo'

function App() {

  return (
    <>
    
      <Routes>
        <Route path='' element={<Index />} />
        <Route path='/login' element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/todoList" element={<TodoList />} />
        <Route path="/todoList/:todoId" element={<TodoDetail />} />
        <Route path="/todoList/new" element={<NewTodo />} />
        <Route path="/todoList/update/:todoId" element={<UpdateTodo />} />
      </Routes>
    
    
    </>
  )
  
}

export default App
