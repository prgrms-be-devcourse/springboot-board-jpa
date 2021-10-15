import React, { useEffect, useState } from 'react';
import axios from 'axios';


function Login(props) {

  // Select Origin
  const local = 'http://localhost:8080';
  const deploy = 'http://15.165.69.116:8080';
  const origin = local;

  // State
  const [loginInfo, setLoginInfo] = useState({
    email: "", password: ""
  });
  const [userInfo, setUserInfo] = useState({
    email: "", password: "", name: "", age: "", hobby: ""
  });

  // Effect
  useEffect(() => {
    delete localStorage.userId;
  }, []);

  // Handler
  const loginEmailHandler = (e) => setLoginInfo({ ...loginInfo, email: e.currentTarget.value });
  const loginPasswordHandler = (e) => setLoginInfo({ ...loginInfo, password: e.currentTarget.value });

  const emailHandler = (e) => setUserInfo({ ...userInfo, email: e.currentTarget.value });
  const passwordHandler = (e) => setUserInfo({ ...userInfo, password: e.currentTarget.value });
  const nameHandler = (e) => setUserInfo({ ...userInfo, name: e.currentTarget.value });
  const ageHandler = (e) => setUserInfo({ ...userInfo, age: e.currentTarget.value });
  const hobbyHandler = (e) => setUserInfo({ ...userInfo, hobby: e.currentTarget.value });

  // Button
  const login = () => {
    axios.post(origin + '/api/user/login', loginInfo)
      .then(res => {
        const userId = res.data.id;
        localStorage.setItem('userId', userId);
        props.history.push("/mypage");
      })
      .catch(err => {
        if (err.response.status == 400 || err.response.status == 404)
          alert(err.response.data.message);
      });
  }

  const signUp = () => {
    axios.post(origin + '/api/user', userInfo).then(
      res => {
        alert("회원가입 완료 (id : " + res.data.id + ")");
      })
      .catch(err => {
        if (err.response.status == 400 || err.response.status == 404)
          alert(err.response.data.message);
      });
  }

  return (
    <div >
      <img style={{ width: "250px", position: "absolute", bottom: "0px", left: "0px" }} src="/img/ms.png" />

      <br />

      <h2>로그인</h2>
      <hr /><br />

      <div className="div_wrapper">
        <input type="text" placeholder="email" onChange={loginEmailHandler} />
        <input type="password" placeholder="password" onChange={loginPasswordHandler} />
        <br /><br />
        <button onClick={login}>로그인하기</button>
      </div>

      <br /><br />

      <h2>회원가입</h2>
      <hr /><br />

      <div className="div_wrapper">
        <input type="email" placeholder="email" onChange={emailHandler} />
        <input type="password" placeholder="password" onChange={passwordHandler} />
        <br />
        <span style={{ fontSize: "13px", color: "red" }}>※주의 : 비번 암호화 안됨 </span>
        <input type="text" placeholder="name" onChange={nameHandler} />
        <input type="number" placeholder="age" onChange={ageHandler} />
        <input type="text" placeholder="hobby" onChange={hobbyHandler} />
        <br /><br />
        <button onClick={signUp}>가입하기</button>
      </div>

    </div>
  );
}

export default Login;
