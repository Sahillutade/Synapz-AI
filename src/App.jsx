import { useState } from 'react'
import './App.css'
import { HashRouter, Route, Routes } from 'react-router-dom'
import { LandingPage } from './components/landing-page'
import { RegisterStep1 } from './components/register-step-1'
import { RegisterOtp } from './components/register-otp'

function App() {

  return (
    <main className="dark bg-background">
      <HashRouter>
      <Routes>
        <Route path='/landingpage' element={<LandingPage />}></Route>
        <Route path='/registerstep1' element={<RegisterStep1 />}></Route>
        <Route path='' element={<RegisterOtp />}></Route>
      </Routes>
    </HashRouter>
    </main>
  )
}

export default App
