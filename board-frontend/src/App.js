import { BrowserRouter, Route, Link } from 'react-router-dom';
import React, { useState } from 'react';
import './App.css';

import Login from './views/Login';
import SignUp from './views/SignUp';
import Mypage from './views/Mypage';
import Board from './views/Board';


function App() {
  return (
    <BrowserRouter>

      <Route path="/" component={ Login } exact />
      <Route path="/signup" component={ SignUp } exact />
      <Route path="/mypage/:userId" component={ Mypage } exact />
      <Route path="/board" component={ Board } exact />

    </BrowserRouter>
  );
}

export default App;
