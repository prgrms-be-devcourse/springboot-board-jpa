import { BrowserRouter, Route, Link } from 'react-router-dom';
import React, { useState } from 'react';
import './App.css';

import Login from './views/Login';
import Mypage from './views/Mypage';
import Board from './views/Board';
import Posting from './views/Posting';
import Post from './views/Post';


function App() {
  return (
    <BrowserRouter>

      <Route path="/" component={ Login } exact />
      <Route path="/mypage" component={ Mypage } exact />
      <Route path="/board" component={ Board } exact />
      <Route path="/posting" component={ Posting } exact />
      <Route path="/post/:postId" component={ Post } exact />

    </BrowserRouter>
  );
}

export default App;
