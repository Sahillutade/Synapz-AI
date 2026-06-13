import { useState } from 'react'
import './App.css'
import { HashRouter, Route, Routes } from 'react-router-dom'
import { LandingPage } from './components/landing-page'

function App() {

  return (
    <HashRouter>
      <Routes>
        <Route path='/' element={<LandingPage />}></Route>
      </Routes>
    </HashRouter>
  )
}

export default App
