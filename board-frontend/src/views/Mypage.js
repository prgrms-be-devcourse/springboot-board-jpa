import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Menu from '../components/Menu';
import { Link } from 'react-router-dom';


function Mypage(props) {

  // Select Origin
  const local = 'http://localhost:8080';
  const deploy = 'http://15.165.69.116:8080';
  const origin = deploy;

  // State
  const userId = localStorage.getItem("userId")
  const [userInfo, setUserInfo] = useState({});
  const [myBoard, setMyBoard] = useState([]);

  // Effect
  useEffect(() => {
    // userInfo
    axios.get(origin + `/api/user/${userId}`)
      .then(res => {
        // console.log(res);
        setUserInfo(res.data)
      })
    // myPostInfo
    axios.get(origin + `/api/post/user/${userId}`)
      .then(res => {
        // console.log(res);
        setMyBoard(res.data)
      })
  }, []);

  // Button
  const resign = () => {
    if (window.confirm("정말 탈퇴하시겠습니까?")) {
      axios.delete(origin + `/api/user/${userId}`)
        .then(res => {
          // console.log(res);
          alert("회원탈퇴 완료. 초기화면으로 이동");
          props.history.push("/");
          return
        })
    }
  }

  return (
    <div >
      <Menu />

      <h2>마이페이지</h2>
      <hr /><br />

      <div className="div_wrapper_mypage">
        <ul>
          <li><b>id</b>: {userInfo.id}</li>
          <li><b>email</b>: {userInfo.email}</li>
          <li><b>password</b>: {userInfo.password}</li>
          <li><b>name</b>: {userInfo.name}</li>
          <li><b>hobby</b>: {userInfo.hobby}</li>
          <li><b>createdAt</b>: {userInfo.createdAt}</li>
        </ul>
        <div style={{ textAlign: "center" }}>
          <button onClick={resign}>회원탈퇴</button>
        </div>
      </div>

      <br /><br /><br />

      <h2>내가 작성한 게시물</h2>
      <hr /><br />
      <div style={{ textAlign: "center" }}>
        <table>
          <tr>
            <th>id(post)</th>
            <th style={{ width: "50%" }}>제목(title)</th>
            <th>조회수(view)</th>
            <th>작성자(createdBy)</th>
          </tr>
          {
            myBoard.map(v =>
              <tr key={v.id}>
                <td>{v.id}</td>
                <td><Link to={`/post/${v.id}`} >{v.title}</Link></td>
                <td>{v.view}</td>
                <td>{v.createdBy}</td>
              </tr>
            )
          }
        </table>

      </div>


    </div>
  );
}

export default Mypage;
