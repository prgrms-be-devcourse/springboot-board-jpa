import { Link, withRouter } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import axios from 'axios';


function Login() {
  // Origin
  const local = 'http://localhost:8080'
  const deploy = 'http://15.165.69.116:8080'

  // State
  const [userInfo, setUserInfo] = useState({
    email: "", password: "", name: "", age: "", hobby: ""
  });

  // Handler
  const emailHandler = (e) => setUserInfo({ ...userInfo, email: e.currentTarget.value });
  const passwordHandler = (e) => setUserInfo({ ...userInfo, password: e.currentTarget.value });
  const nameHandler = (e) => setUserInfo({ ...userInfo, name: e.currentTarget.value });
  const ageHandler = (e) => setUserInfo({ ...userInfo, age: e.currentTarget.value });
  const hobbyHandler = (e) => setUserInfo({ ...userInfo, hobby: e.currentTarget.value });

  // Button
  const signUp = () => {
    axios.post(deploy + '/api/user', userInfo)
      .then(
        res => {
          console.log(res)
        }
      )
  }

  return (
    <div >

      <h2>로그인</h2>
      <hr /><br />

      <div className="div_wrapper">
        <input type="text" placeholder="email" />
        <input type="password" placeholder="password" />
        <br /><br />
        <button >로그인하기</button>
      </div>

      <br /><br /><br />

      <h2>회원가입</h2>
      <hr /><br />

      <div className="div_wrapper">
        <input type="email" placeholder="email" onChange={emailHandler} />
        <input type="password" placeholder="password" onChange={passwordHandler} />
        <input type="text" placeholder="name" onChange={nameHandler} />
        <input type="number" placeholder="age" onChange={ageHandler} />
        <input type="text" placeholder="hobby" onChange={hobbyHandler} />
        <br /><br />
        <button onClick={signUp}>가입하기</button>
      </div>

    </div>
  );
}

export default withRouter(Login);
