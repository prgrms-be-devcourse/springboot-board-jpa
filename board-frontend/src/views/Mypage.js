import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Menu from '../components/Menu';


function Mypage(props) {

  // Select Origin
  const local = 'http://localhost:8080';
  const deploy = 'http://15.165.69.116:8080';
  const origin = local;

  // State
  const [userInfo, setUserInfo] = useState({});
  const [myPostInfo, setMyPostInfo] = useState([]);

  // Effect
  useEffect(() => {
    const userId = localStorage.getItem("userId");
    if (userId == null) {
      alert("로그인 정보가 없습니다");
      props.history.push("/");
      return
    }
    // userInfo
    axios.get(origin + "/api/user/" + userId)
      .then(res => {
        console.log(res);
        setUserInfo(res.data)
      })
    // myPostInfo
    axios.get(origin + "/api/post/user/" + userId)
      .then(res => {
        console.log(res);
        setMyPostInfo(res.data)
      })
  }, []);

  // Button
  const resign = () => {
    axios.delete(origin + "/api/user/" + userInfo.id)
      .then(res => {
        console.log(res);
        alert("회원탈퇴 완료. 초기화면으로 이동");
        props.history.push("/");
        return
      })
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

      <h2>내가 쓴 게시물</h2>
      <hr /><br />
      <div style={{ textAlign: "center" }}>
        <table>
          <tr>
            <th>id(post)</th>
            <th>title</th>
            <th>content</th>
            <th>createdAt</th>
            <th>createdBy</th>
            <th></th>
          </tr>
          {
            myPostInfo.map(v =>
              <tr key={v.id}>
                <td>{v.id}</td>
                <td>{v.title}</td>
                <td>{v.content}</td>
                <td>{v.createdAt}</td>
                <td>{v.userDto.email}</td>
                <td><button>삭제</button></td>
              </tr>
            )
          }
        </table>

      </div>


    </div>
  );
}

export default Mypage;
