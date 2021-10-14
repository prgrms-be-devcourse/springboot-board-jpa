import { Link } from 'react-router-dom';
import React, { useEffect, useState } from 'react';

function Menu() {

  // Effect
  useEffect(() => {
    if (localStorage.getItem("userId") == null) {
      alert("로그인 정보가 없습니다");
      window.location.replace("/");
      return
    }});

    const logout = () => {
        alert("로그아웃 완료. 초기화면으로 이동")
    }

  return (
    <div >
        <div className= "div_menu">
            <Link to="/board/1">게시판</Link>
            &nbsp;&nbsp;&nbsp;
            <Link to="/mypage">마이페이지</Link>
            &nbsp;&nbsp;&nbsp;
            <Link to="/" onClick={logout}>로그아웃</Link>
            <br />
        </div>
    </div>
  );
}

export default Menu;
