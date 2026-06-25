import { useState } from 'react'
import './App.css'
import { HashRouter, Route, Routes } from 'react-router-dom'
import { LandingPage } from './components/landing-page'
import { RegisterStep1 } from './components/register-step-1'
import { RegisterOtp } from './components/register-otp'
import { Subscription } from './components/subscription'
import { Login } from './components/login'
import { AuthSuccess } from './components/auth-success'
import { ChatPage } from './components/chat'

function App() {

  return (
    <main className="dark bg-background">
      <HashRouter>
      <Routes>
        <Route path='/' element={<LandingPage />}></Route>
        <Route path='/registerstep1' element={<RegisterStep1 />}></Route>
        <Route path='/registerotp/:registrationToken' element={<RegisterOtp />}></Route>
        <Route path='/login' element={<Login />}></Route>
        <Route path='/subscription' element={<Subscription />}></Route>
        <Route path='/login-success' element={<AuthSuccess />}></Route>
        <Route path='/chat' element={<ChatPage />}></Route>
      </Routes>
    </HashRouter>
    </main>
  )
}

export default App
