import { useState } from 'react'
import './App.css'
import { HashRouter, Route, Routes } from 'react-router-dom'
import { LandingPage } from './components/landing-page'

function App() {

  return (
    <main className="dark bg-background">
      <HashRouter>
      <Routes>
        <Route path='/' element={<LandingPage />}></Route>
      </Routes>
    </HashRouter>
    </main>
  )
}

export default App
